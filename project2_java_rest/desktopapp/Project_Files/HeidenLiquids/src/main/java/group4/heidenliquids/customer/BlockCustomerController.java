/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenliquids.customer;

import dao.AbstractDAOFactory;
import dao.DAO;
import entities.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen
 * {@code 423121@student.fontys.nl}
 */
public class BlockCustomerController implements Initializable {

    @FXML
    private ListView<Customer> listCustomer;
    @FXML
    private Label lbCompanyName;
    @FXML
    private Label lbFirstName;
    @FXML
    private Label lbLastName;
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbTaxNr;
    @FXML
    private Label lbTelephone;
    @FXML
    private Label lbFirstNameResp;
    @FXML
    private Label lbLastNameResp;
    @FXML
    private Label lbFaxNr;
    @FXML
    private Label lbCustomerNr;
    @FXML
    private TextField textSearch;
    @FXML
    private Button btSearch;
    @FXML
    private Label lbBlockStatusRed;
    @FXML
    private Label lbBlockStatusGreen;

    ObservableList<Customer> customers;

    Customer customerSelected;

    AbstractDAOFactory factory;

    Callback<Class<?>, Object> controllerFactory;

    

    /**
     *
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public BlockCustomerController(AbstractDAOFactory factory, Callback<Class<?>, Object> controllerFactory) {
        this.factory = factory;
        this.controllerFactory = controllerFactory;
    }

    /**
     *
     * Handles the event of the block button.
     *
     * when the block button is pressed, the customers block status gets updated
     * in the database
     *
     * @param event
     */
   

    /**
     *
     * Creates a List of all customers for the list view.
     */
    

    @FXML
    private void handlerListClick(MouseEvent event) {
        this.customerSelected = (Customer) this.listCustomer.getSelectionModel().getSelectedItem();

        this.textSearch.setText("");
        this.lbBlockStatusGreen.setText("");
        this.lbBlockStatusRed.setText("");

        this.lbCompanyName.setText(this.customerSelected.getCompanyName());
        this.lbFirstName.setText(this.customerSelected.getFirstName());
        this.lbLastName.setText(this.customerSelected.getLastName());
        this.lbEmail.setText(this.customerSelected.getEmail());
        this.lbTaxNr.setText(this.customerSelected.getTaxNumber());
        this.lbTelephone.setText(this.customerSelected.getPhoneNumber());
        this.lbFirstNameResp.setText(this.customerSelected.getFirstNameResp());
        this.lbLastNameResp.setText(this.customerSelected.getLastNameResp());
        this.lbFaxNr.setText(this.customerSelected.getFaxNumber());
        this.lbCustomerNr.setText(this.customerSelected.getId());
      
        if(this.customerSelected.isBlockStatus())
            this.lbBlockStatusRed.setText("BLOCKED");
        else
            this.lbBlockStatusGreen.setText("NOT Blocked");

        this.listCustomer.setItems(FXCollections.observableArrayList());
    }

    @FXML
    private void handlerBtSearch(ActionEvent event) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        DAO<String, Customer> dao = this.factory.createDao(Customer.class);

        customers.addAll(dao.getByColumnValue("companyname", textSearch.getText()));
        System.out.println(customers);

        this.listCustomer.setItems(customers);
    }
}
