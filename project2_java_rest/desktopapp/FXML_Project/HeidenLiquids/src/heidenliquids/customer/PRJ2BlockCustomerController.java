/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heidenliquids.customer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen
 * {@code 423121@student.fontys.nl}
 */
public class PRJ2BlockCustomerController implements Initializable {

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
    private RadioButton btBlock;

    /**
     *
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.makeCustomerList();
    }    

    @FXML
    private void handlerBtBlocked(ActionEvent event) {
        //block customer
    }
    
    private void makeCustomerList() {
        ObservableList<Customer> items =FXCollections.observableArrayList(new Customer
        ("Ring Police", "Lukas", "Luhr", "info@ringpolice.de", "123wasd:68", "+49284112346", "Sebastian", "Stadler", "12345678", false, 01), 
                new Customer
        ("JP Performance GMBH", "JP", "Kreamer", "info@jpp.de", "123wa4d:68", "+4435682346", "Sebastian", "Kubik", "87654321", false, 02));
        
        this.listCustomer.setItems(items);
    }

    @FXML
    private void handlerListClick(MouseEvent event) {
        this.lbCompanyName.setText(listCustomer.getSelectionModel().getSelectedItem().getCompanyName());
        this.lbFirstName.setText(this.listCustomer.getSelectionModel().getSelectedItem().getFirstName());
        this.lbLastName.setText(this.listCustomer.getSelectionModel().getSelectedItem().getLastName());
        this.lbEmail.setText(this.listCustomer.getSelectionModel().getSelectedItem().getEmail());
        this.lbTaxNr.setText(this.listCustomer.getSelectionModel().getSelectedItem().getTaxNumber());
        this.lbTelephone.setText(this.listCustomer.getSelectionModel().getSelectedItem().getPhoneNumber());
        this.lbFirstNameResp.setText(this.listCustomer.getSelectionModel().getSelectedItem().getFirstNameResp());
        this.lbLastNameResp.setText(this.listCustomer.getSelectionModel().getSelectedItem().getLastNameResp());
        this.lbFaxNr.setText(this.listCustomer.getSelectionModel().getSelectedItem().getFaxNumber());
        this.lbCustomerNr.setText(this.listCustomer.getSelectionModel().getSelectedItem().getCustomerNumber() + "");
        
    }

    private void showCustomer() {
    }
    
}
