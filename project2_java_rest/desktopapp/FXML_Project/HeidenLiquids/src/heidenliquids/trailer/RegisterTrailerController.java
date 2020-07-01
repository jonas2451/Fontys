/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heidenliquids.trailer;

import java.awt.Choice;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen
 * {@code j.terschlusen@student.fontys.nl}
 */
public class RegisterTrailerController implements Initializable {
    
    @FXML
    private TextField textLicenseTrailer;
    @FXML
    private TextField textWeightTrailer;
    @FXML
    private ChoiceBox DDTrailer;
    @FXML
    private Button btSubmit;
    @FXML
    private Button btCancel;
    @FXML
    private Label lbErrors;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.createChoiceBox();
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        if (!this.textWeightTrailer.getText().matches("[0-9.]*")) {
            this.textWeightTrailer.clear();
            this.lbErrors.setText("Error! Weight should be a number!");
        } else {
            this.lbErrors.setText("");
            
            Trailer trailer = new Trailer(this.textLicenseTrailer.getText(), Double.parseDouble(this.textWeightTrailer.getText()), this.DDTrailer.getValue().toString(), 0);
            
            System.out.println("Trailer: " + trailer.toString());
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    
    
    private void createChoiceBox() {
        ObservableList<String> list = FXCollections.observableArrayList("low loader", "container", "low loading container");
        
        this.DDTrailer.setItems(list);
        

    }

}
