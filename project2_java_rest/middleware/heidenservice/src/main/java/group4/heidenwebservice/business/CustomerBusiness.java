/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenwebservice.business;

import dao.DAO;
import dao.PG.PGDAO;
import entities.Address;
import entities.Customer;
import group4.heidenwebservice.Controller;
import java.util.Collection;
import javafx.collections.ObservableList;

/**
 *
 * @author sajee
 */
public class CustomerBusiness implements Business {
    
    PGDAO<String, Customer> customerDao;
    PGDAO<String, Address> addressDao;

    Controller controller;
    Customer customer;

    /**
     *
     * @param customerDao
     * @param addressDao
     */
    public CustomerBusiness(PGDAO customerDao, PGDAO addressDao) {
        this.customerDao = customerDao;
        this.addressDao = addressDao;
    }

    void saveCustomer() {
        customerDao.save(customer);
    }
    
     public void printTest(){
        System.out.println("It actually works! o/");
    }

    public ObservableList<Customer> searchCustomers(String searchTerm) {

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
