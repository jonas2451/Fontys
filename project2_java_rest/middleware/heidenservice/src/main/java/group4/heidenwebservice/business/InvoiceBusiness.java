/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenwebservice.business;

import dao.DAO;
import dao.PG.PGDAO;
import entities.Invoice;
import entities.Order;
import group4.heidenwebservice.Controller;
import java.util.Collection;
import javafx.collections.ObservableList;

/**
 *
 * @author sajee
 */
public class InvoiceBusiness implements Business {

    
    PGDAO<String, Invoice> invoiceDao;
    PGDAO<String, Order> orderDao;
    
    

    Controller controller;
    Invoice invoice;

    /**
     *
     * @param invoiceDao
     * @param orderDao
     */
    public InvoiceBusiness(PGDAO invoiceDao, PGDAO orderDao) {
        this.invoiceDao = invoiceDao;
        this.orderDao = orderDao;
    }

    void saveInvoice() {
        invoiceDao.save(invoice);
    }

//    Order makeOrder(LocalDate localDate) {
//        return new Order(GUID.generate(), localDate);
//    }

    public void printTest(){
        System.out.println("It actually works! o/");
    }

    public ObservableList<Order> searchOrders(String searchTerm) {

        //ObservableList<Customer> list = FXCollections.observableArrayList(customerDao.searchFor(new Customer("empty"), searchTerm));
        return null;
    }

    @Override
    public void addController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Collection getDao() {
        return null;
    }

    @Override
    public void setDao(DAO... dao) {

    }
    
}
