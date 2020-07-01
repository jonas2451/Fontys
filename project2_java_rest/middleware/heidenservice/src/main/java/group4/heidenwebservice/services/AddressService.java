package group4.heidenwebservice.services;

import com.google.gson.Gson;
import dao.AbstractDAOFactory;
import dao.DAO;
import dao.DAOConnection;
import entities.Address;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

@Path("address")
public class AddressService {


    private AbstractDAOFactory factory;
    private DAOConnection conn;

    @Inject
    public AddressService(AbstractDAOFactory factory, DAOConnection daoConnection) {
        this.factory = factory;
        this.conn = daoConnection;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String readAll() {
        DAO dao = factory.createDao(String.class, Address.class, conn);

        Collection<Address> addresses = new ArrayList<>();
        addresses.addAll(dao.getAll());
        Gson gson = new Gson();
        String json = gson.toJson(addresses);

        System.out.println("Address readAll(): " + json);

        return json;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String save(Address e) {
        System.out.println("<Address save()> ADDRESS: " + e);



        DAO dao = this.factory.createDao(String.class, Address.class, conn);
//        System.out.println("<Address save()> DAO: " + dao);
        Address address = (Address) dao.save(e);
        System.out.println("<Address save()> RETURNED ADDRESS: " + address);
        Gson gson = new Gson();
        String json = gson.toJson(address);
        System.out.println("<Address save()> json: " + json);
        return json;
    }


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String read(@PathParam("id") String id) {
        DAO dao = this.factory.createDao(String.class, Address.class, conn);

        return new Gson().toJson(dao.get(id).get());
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(Address e) {
        DAO dao = this.factory.createDao(String.class, Address.class, conn);
    }

    @GET
    @Path("{column}/{value}")
    @Produces
    public String getByColumnValue(@PathParam("column") String column, @PathParam("value") String value) {
        DAO dao = factory.createDao(String.class, Address.class, conn);
        return new Gson().toJson(dao.getByColumnValue(column, value));
    }
}
