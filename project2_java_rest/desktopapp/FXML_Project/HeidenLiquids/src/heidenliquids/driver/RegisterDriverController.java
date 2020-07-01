/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heidenliquids.driver;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sajee
 */
public class RegisterDriverController implements Initializable {

    @FXML
    private TextField driverFirstName;
    @FXML
    private TextField driverLastName;
    @FXML
    private TextField driverAddress;
    @FXML
    private Button register;
    @FXML
    private TextField driverEmail;
    @FXML
    private TextField driverAccountNumber;
    @FXML
    private TextField driverLicense;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private DatePicker dateOfJoining;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void registerDriver(ActionEvent event) {
        System.out.println("Driver has been registered");
    }
    
}
