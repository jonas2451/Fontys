/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenwebservice.services;

import com.google.gson.Gson;
import dao.AbstractDAOFactory;
import dao.DAO;
import dao.DAOConnection;
import entities.ExecuteOrder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;

/**
 *
 * @author rajinder
 */
@Path("executeorder")
public class ExecuteOrderService {

    private AbstractDAOFactory factory;
    private DAOConnection conn;

    @Inject
    public ExecuteOrderService(AbstractDAOFactory factory, DAOConnection daoConnection) {
        this.factory = factory;
        this.conn = daoConnection;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String readAll() {

        DAO dao = factory.createDao(String.class, ExecuteOrder.class, conn);
        Collection<ExecuteOrder> executeOrders = new ArrayList<>();
        executeOrders.addAll(dao.getAll());
        Gson gson = new Gson();
        String json = gson.toJson(executeOrders);
        System.out.println("ExecuteOrder readAll(): " + json);
        return json;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String save(ExecuteOrder e) {
        DAO dao = factory.createDao(String.class, ExecuteOrder.class, conn);
        return new Gson().toJson(dao.save(e));
    }
    
}
