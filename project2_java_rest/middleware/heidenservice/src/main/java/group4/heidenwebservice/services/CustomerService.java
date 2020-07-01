package group4.heidenwebservice.services;

import com.google.gson.Gson;
import dao.AbstractDAOFactory;
import dao.DAO;
import dao.DAOConnection;
import dao.DAOException;
import entities.Customer;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

import group4.heidenwebservice.exceptions.CustomerAllreadyRegisteredException;

@Path("customer")
public class CustomerService {

    

    AbstractDAOFactory factory;
    DAOConnection conn;

    @Inject
    public CustomerService(AbstractDAOFactory factory, DAOConnection conn) {
        this.factory = factory;
        this.conn = conn;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        DAO dao = this.factory.createDao(String.class, Customer.class, conn);

        return new Gson().toJson(dao.getAll());
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String save(Customer customer) {
        System.out.println("<CustomerService save()> e: " + customer);

        DAO<String, Customer> customerDao = factory.createDao(String.class, Customer.class, conn);

        Customer newE = customerDao.save(customer);
        System.out.println("<CustomerService save()> newE: " + newE);
        Gson gson = new Gson();
        String json = gson.toJson(newE);
        return json;
    }
    
    public void validateCustomer(Customer customer) {
        ArrayList<Customer> list = new ArrayList<>();
        DAO dao = factory.createDao(String.class, Customer.class, conn);
        list.addAll(dao.getByColumnValue("companyname", customer.getCompanyName()));
        
        if(list.isEmpty()){
            throw new CustomerAllreadyRegisteredException();
        }
    }

    @GET
    @Path("{column}/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByColumnValue(@PathParam("column") String column, @PathParam("value") String value) {
        try {
            DAO dao = this.factory.createDao(String.class, Customer.class, conn);

            ArrayList<Customer> list = new ArrayList<>();
            list.addAll(dao.getByColumnValue(column, value));

            if (!list.isEmpty()) {
                return Response.status(Response.Status.OK).entity(new Gson().toJson(list)).build();
            }
                return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DAOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "getByColumnValue operation failed.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
}
