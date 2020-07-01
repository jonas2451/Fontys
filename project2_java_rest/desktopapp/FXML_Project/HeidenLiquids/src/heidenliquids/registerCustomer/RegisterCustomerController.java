/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heidenliquids.registerCustomer;

import java.net.URL;
import java.util.ResourceBundle;

import heidenliquids.customer.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sajee
 */
public class RegisterCustomerController implements Initializable {

    @FXML
    private TextField customerName;
    @FXML
    private TextField customerAddress;
    @FXML
    private TextField taxNumber;
    @FXML
    private TextField faxNumber;
    @FXML
    private TextField invoiceAddress;
    @FXML
    private Button register;
    @FXML
    private Button block;
    @FXML
    private TextField customerEmail;

    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void registerCustomer(ActionEvent event) {
        String name = this.customerName.getText();
        String email = this.customerEmail.getText();
        String address = this.customerAddress.getText();
        String taxNr = this.taxNumber.getText();
        String faxNr = this.faxNumber.getText();
        String invoiceAddress = this.invoiceAddress.getText();

        Customer newCustomer = new Customer(name, "Christiane", "Holz", email, taxNr, "123091734", "test1", "test2", faxNr, false, 3);

        /*System.out.println(name);
        System.out.println(address);
        System.out.println(email);
        System.out.println(taxNr);
        System.out.println(faxNr);
        System.out.println(invoiceAddress);
        
        System.out.println();
        System.out.println();*/

        System.out.println(newCustomer);

        System.out.println("Customer has been registered");
    }

    @FXML
    private void blockCustomer(ActionEvent event) {
        System.out.println("Customer has been blocked");
    }
    
}
