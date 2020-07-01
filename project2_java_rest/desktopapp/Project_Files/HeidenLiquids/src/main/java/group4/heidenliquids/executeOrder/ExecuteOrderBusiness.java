/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenliquids.executeOrder;

import dao.AbstractDAOFactory;
import dao.DAO;
import dao.Entity1;
import entities.ExecuteOrder;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author rajinder
 */
public class ExecuteOrderBusiness {

    AbstractDAOFactory factory;

    public ExecuteOrderBusiness(AbstractDAOFactory factory) {
        this.factory = factory;
    }

    public Entity1 save(Entity1 e) {
        DAO dao = factory.createDao(ExecuteOrder.class);
        return dao.save(e);
    }

    public Collection getExecuteOrder(String driverID) {
        DAO dao = factory.createDao(ExecuteOrder.class);
//        List<ExecuteOrder> executeOrders = dao.getAll().forEach();
        return dao.getAll();
    }
}
