/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenwebservice.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AbstractDAOFactory;
import dao.DAO;
import dao.DAOConnection;
import dao.DAOException;
import dao.PG.PGDAOFactory;
import dao.REST.LocalDateJsonAdapter;
import entities.Invoice;
import entities.Order;
import java.time.LocalDate;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author sajee
 */
@Path("invoice")
public class InvoiceService {
    
    private AbstractDAOFactory factory;
    private DAOConnection conn;
    
    @Inject
    public InvoiceService (AbstractDAOFactory factory, DAOConnection daoConnection){
        this.factory = factory;
        this.conn = daoConnection;
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String readAll() {

        DAO dao = this.factory.createDao(String.class, Invoice.class, conn);

        return new Gson().toJson(dao.getAll());
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String save(Invoice invoice) {
        System.out.println("<Invoice save()> INVOICE: " + invoice);

        DAO<String, Invoice> invoiceDAO = this.factory.createDao(String.class, Invoice.class, conn);
        Invoice newE = invoiceDAO.save(invoice);
        
        System.out.println("<InvoiceService save()> newE: " + newE);
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter());
        return gsonBuilder.create().toJson(newE);
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
//        try {
//            DAO<String, Order> orderDAO = factory.createDao(String.class, Order.class, conn);
//            Optional o = orderDAO.get(id);
//
//            if (o.isPresent()) return Response.status(Response.Status.OK).entity(new Gson().toJson(o.get())).build();
//            return Response.status(Response.Status.NOT_FOUND).build();
//        } catch (DAOException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//        }
    }

    public void validateInvoice(Invoice invoice) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
