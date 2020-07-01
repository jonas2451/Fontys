package group4.heidenwebservice.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AbstractDAOFactory;
import dao.DAO;
import dao.DAOConnection;
import dao.DAOException;
import dao.REST.LocalDateJsonAdapter;
import entities.Action;
import entities.Order;
import entities.OrderProduct;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * REST service for the order-entity.
 * @author Jonas Teschlüsen
 */
@Path("order")
public class OrderService {


    AbstractDAOFactory factory;
    DAOConnection conn;
    final GsonBuilder gsonBuilder =
            new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter())
                .excludeFieldsWithModifiers(Modifier.STATIC);

    @Inject
    public OrderService(AbstractDAOFactory factory, DAOConnection daoConnection) {
        this.factory = factory;
        this.conn = daoConnection;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String readAll() {
        DAO<String, Order> dao = this.factory.createDao(String.class, Order.class, conn);

        Collection<Order> orders = dao.getAll();

        orders.forEach(this::rebuildOrder);

        Gson gson = this.gsonBuilder.create();
        System.out.println(orders);
        String json = gson.toJson(orders);
        System.out.println(json);
        return json;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response save(String s) {
        System.out.println("<OrderService save()> order: " + s);

        Order order = null;

        try {
            order = this.gsonBuilder.create().fromJson(s, Order.class);

            DAO<String, Order> orderDAO = factory.createDao(String.class, Order.class, conn);
            DAO<String, OrderProduct> orderProductDAO = factory.createDao(String.class, OrderProduct.class, conn);

            Order newOrder = orderDAO.save(order);
            ArrayList<OrderProduct> newOrderProducts = new ArrayList<>(orderProductDAO.saveAll(order.getOrderProducts()));

            for (int i = 0; i < newOrderProducts.size(); i++) {
                DAO<String, Action> actionDAO = factory.createDao(String.class, Action.class, conn);
                for (Action action1 : order.getOrderProducts().get(i).getActions()) {
                    newOrderProducts.get(i).addAction(actionDAO.save(action1));
                }
            }
            newOrder.setOrderProducts(newOrderProducts);
            //Build json
            System.out.println("<OrderService save()> newE: " + newOrder);
            Logger.getLogger(this.getClass().getName()).log(Level.FINE,
                    "Order, OrderProducts & Actions have been saved successfully.");

            return Response.status(Response.Status.OK).entity(this.gsonBuilder.create().toJson(newOrder)).build();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "Order violated constraint: ", ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (DAOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "Dao failed to save the entity: " + order, ex);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        try {
            DAO<String, Order> orderDAO = factory.createDao(String.class, Order.class, conn);
            Optional<Order> o = orderDAO.get(id);

            if (o.isPresent()) {
                Order order = this.rebuildOrder(o.get());
                return Response.status(Response.Status.OK).entity(this.gsonBuilder.create().toJson(order)).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DAOException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
        DAO<String, Order> dao = factory.createDao(String.class, Order.class, conn);
        try {
            Optional o = dao.get(id);
            if (o.isPresent()) {
                dao.delete((Order)o.get());
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DAOException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Order rebuildOrder(Order order) {
        DAO<String, OrderProduct> orderProductDAO = factory.createDao(String.class, OrderProduct.class, conn);
        Collection<OrderProduct> products = orderProductDAO.getAll()
                .stream()
                .filter(o -> o.getOrderid().equals(order.getId()))
                .collect(Collectors.toList());



        products.forEach(p -> {
                    DAO<String, Action> actionDAO = factory.createDao(String.class, Action.class, conn);
                    Collection<Action> actions = actionDAO.getAll().stream()
                            .filter(a -> a.getProductId().equals(p.getId()))
                            .collect(Collectors.toList());
                    p.setActions((ArrayList<Action>) actions);
                });

        order.setOrderProducts((ArrayList<OrderProduct>) products);

        return order;
    }
}
