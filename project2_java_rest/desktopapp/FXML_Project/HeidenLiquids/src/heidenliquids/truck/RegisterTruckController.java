/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heidenliquids.truck;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author rajinder
 */
public class RegisterTruckController implements Initializable {

    @FXML
    private TextField licenseNumber;
    @FXML
    private TextField maximumTowingWeight;
    @FXML
    private TextField typeOfTruck;
    @FXML
    private TextField maximumWeight;
    @FXML
    private Button cancelButton;
    @FXML
    private Button registerTruckButton;
    @FXML
    private RadioButton availableButton;
    @FXML
    private RadioButton notAvailableButton;
    @FXML
    private Label result;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handlerCancel(ActionEvent event) {
    }

    @FXML
    private void handlerRegisterTruck(ActionEvent event) {
        String licenseNr;
        String maxTowingWeight;
        String maxWeight;
        String availableButton;
        String notavailableButton;
        String typeOfTruck;

        licenseNr = this.licenseNumber.getText();
        maxTowingWeight = this.maximumTowingWeight.getText();
        maxWeight = this.maximumWeight.getText();
        availableButton = this.availableButton.getText();
        notavailableButton = this.notAvailableButton.getText();
        typeOfTruck = this.typeOfTruck.getText();

        if (licenseNr.isEmpty() || maxTowingWeight.isEmpty()|| maxWeight.isEmpty() ||  typeOfTruck.isEmpty()) {
            result.setText("Please enter all fields!");
        } else {
            result.setText("truck has been registered!");
        }
    }

}
