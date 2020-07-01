package group4.heidenwebservice.services;

import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;
import dao.*;
import entities.Action;
import entities.Address;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Reader;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("action")
public class ActionService {

    DAO dao;
    DAOConnection connection;

    @Inject
    public ActionService(AbstractDAOFactory factory, DAOConnection connection) {
        this.dao = factory.createDao(String.class, Action.class, connection);
        this.connection = connection;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        return new Gson().toJson(dao.getAll());
//        return dao.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        try {
            Optional<Action> o = dao.get(id);
            if (o.isPresent()) {
                return Response.status(Response.Status.OK).entity(new Gson().toJson(o.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (DAOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "get operation failed.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Action action) {
        try {
            String json = new Gson().toJson(dao.save(action));
            return Response.status(Response.Status.OK).entity(json).build();
        } catch (DAOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "save operation failed.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
        try {
            Optional o = dao.get(id);
            if (o.isPresent()) {
                dao.delete((Entity1) o.get());
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DAOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "delete operation failed.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }


    }
}
