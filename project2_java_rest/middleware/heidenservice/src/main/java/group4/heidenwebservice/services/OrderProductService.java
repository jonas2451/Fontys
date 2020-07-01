package group4.heidenwebservice.services;

import com.google.gson.Gson;
import dao.AbstractDAOFactory;
import dao.DAO;
import dao.DAOConnection;
import entities.Address;
import entities.OrderProduct;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Path("orderproduct")
public class OrderProductService {


    AbstractDAOFactory factory;
    DAOConnection connection;

    @Inject
    public OrderProductService(AbstractDAOFactory factory, DAOConnection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<OrderProduct> getAll(){
        DAO dao = this.factory.createDao(String.class, OrderProduct.class, connection);

        return dao.getAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String save(OrderProduct e) {
        DAO dao = this.factory.createDao(String.class, OrderProduct.class, connection);

        OrderProduct orderProduct = (OrderProduct) dao.save(e);

        return new Gson().toJson(orderProduct);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") String id) {
        DAO dao = this.factory.createDao(String.class, OrderProduct.class, this.connection);

        return new Gson().toJson(dao.get(id));
    }

    @GET
    @Path("{column}/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByColumnValue(@PathParam("column") String column, @PathParam("value") String value) {
        DAO dao = this.factory.createDao(String.class, OrderProduct.class, this.connection);
        ArrayList<OrderProduct> list = new ArrayList<>();
        list.addAll(dao.getByColumnValue(column, value));
        if (list.size() > 0) {
            return Response.status(Response.Status.OK).entity(new Gson().toJson(list)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
