/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heidenliquids.address;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen
 * {@code 423121@student.fontys.nl}
 */
public class PRJ2AddressController implements Initializable {
    
    Address newAddress;
    
    @FXML
    private TextField textCity;
    @FXML
    private TextField textZIP;
    @FXML
    private TextField textNumber;
    @FXML
    private TextField textStreet;
    @FXML
    private Button btSubmit;
    @FXML
    private Button btCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handlerSubmit(ActionEvent event) {
        this.makeLocation();
        System.out.println(this.newAddress.toString());
    }

    @FXML
    private void handlerCancel(ActionEvent event) {
        System.exit(1);
    }
    
    /**
     * 
     * creates a location from the information entered in the application
     */
    private void makeLocation() {
        this.newAddress = new Address(this.textCity.getText(), this.textZIP.getText(), this.textStreet.getText(), this.textNumber.getText());
    }
}
