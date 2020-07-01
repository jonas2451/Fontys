/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenliquids.customer;

import dao.AbstractDAOFactory;
import dao.DAO;
import dao.util.GUID;
import entities.Address;
import entities.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author sajee
 */
public class RegisterCustomerController implements Initializable {

    
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfTaxNr;
    @FXML
    private TextField tfPhoneNr;
    @FXML
    private Button btRegister;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfFirstNameResponsiblePerson;
    @FXML
    private TextField tfLastNameResponsiblePerson;
    @FXML
    private TextField tfFaxNr;
    @FXML
    private TextField tfCompanyName;
    @FXML
    private Label lbresult;
    @FXML
    private Label lbError;
    @FXML
    private ListView<Address> listAddresses;
    @FXML
    private Label lbCustomerNr;
    @FXML
    private Label lbAddressId;
    
    ObservableList<Address> addresses;
    
    private AbstractDAOFactory factory;
    
    Callback<Class<?>, Object> controllerFactory;
    
    Address addressSelected;
    
    Customer customer;

    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.makeAddressList();
    }   
    
    public RegisterCustomerController(AbstractDAOFactory factory, Callback<Class<?>, Object> controllerFactory) {
        this.factory = factory;
        this.controllerFactory = controllerFactory;
    }


    @FXML
    private void btRegisterHandler(ActionEvent event) {
        if (this.validate()) {
            DAO<String, Customer> dao = this.factory.createDao(Customer.class);
            this.lbCustomerNr.setText(GUID.generate());
            
            this.customer = new Customer(this.lbCustomerNr.getText(), this.tfCompanyName.getText(), this.tfFirstName.getText(), 
                    this.tfLastName.getText(), this.tfEmail.getText(), this.tfTaxNr.getText(), this.tfPhoneNr.getText(),
                    this.tfFirstNameResponsiblePerson.getText(), this.tfLastNameResponsiblePerson.getText(), 
                    this.tfFaxNr.getText(), false,
                    this.lbAddressId.getText());
            
            dao.save(this.customer);
        }     
    }
    
     private boolean validate() {

        if (this.tfCompanyName.getText().isEmpty() || this.tfFirstName.getText().isEmpty()
                || this.tfLastName.getText().isEmpty() || this.tfEmail.getText().isEmpty()
                || this.tfTaxNr.getText().isEmpty() || this.tfPhoneNr.getText().isEmpty()
                || this.tfFirstNameResponsiblePerson.getText().isEmpty() || this.tfLastNameResponsiblePerson.getText().isEmpty()
                || this.tfFaxNr.getText().isEmpty()) {
            this.lbError.setText("");
            this.lbresult.setText("");
            this.lbError.setText("! Please fill in all fields");
            return false;
        }
        if(this.lbAddressId.getText().isEmpty()){
            this.lbError.setText("");
            this.lbresult.setText("");
            this.lbError.setText("! Please select an Address");
            return false;
        }
        this.lbresult.setText("");
        this.lbError.setText("");
        this.lbresult.setText("Your customer has been registered");
        return true;
    }
        
    
    
     private void makeAddressList() {
        this.addresses = FXCollections.observableArrayList();

        DAO dao = this.factory.createDao(Address.class);

        addresses.addAll(dao.getAll());
        System.out.println(addresses);

        this.listAddresses.setItems(this.addresses);
     }

    @FXML
    private void handlerAddListClick(MouseEvent event) {
        this.addressSelected = (Address) this.listAddresses.getSelectionModel().getSelectedItem();
        this.lbAddressId.setText(this.addressSelected.getId());
    }
    
}
