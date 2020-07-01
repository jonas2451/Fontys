/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenliquids.trailer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.PG.connection.Connect;
import dao.PG.connection.PgJDBC;
import entities.Trailer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen
 * {@code j.terschlusen@student.fontys.nl}
 */
public class RegisterTrailerController implements Initializable {

    @FXML
    public Label lbTrailerType;
    @FXML
    public Label lbLicense;
    @FXML
    public Label lbWeight;
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

    Connection conn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            this.conn = Connect.createConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.createChoiceBox();
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        if (this.validate()) {
            Trailer trailer = new Trailer(this.textLicenseTrailer.getText(),
                    Double.parseDouble(this.textWeightTrailer.getText()), this.DDTrailer.getValue().toString(), 0);

            String query = "insert into trailers values('" + trailer.getLicenseNumber()+"', '" + trailer.getMaxWeight() +
                    "', '" + trailer.getTrailerType() + "', '" + trailer.getCurrentWeightLoaded() + "');";

            PgJDBC.doQuery(conn, query);

            System.out.println("Trailer: " + trailer.toString());

            this.cleanUp();
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

    private boolean validate() {

        if (this.textLicenseTrailer.getText().isEmpty()) {
            this.lbErrors.setText("Error! Enter a license!");
            return false;
        }

        //the following regex checks firstly for x amount of numbers between 0 and 9, afterwards it checks for 1 . and lastly it checks for more numbers after the .
        if (!this.textWeightTrailer.getText().matches("[0-9]*\\.?[0-9]*") || this.textWeightTrailer.getText().isEmpty()) {
            this.textWeightTrailer.clear();
            this.lbErrors.setText("Error! Weight should be a number!");
            return false;
        } else {
            this.lbErrors.setText("");
        }

        if (this.DDTrailer.getSelectionModel().getSelectedItem() == null) {
            this.lbErrors.setText("Error! Select a 'trailer type'!");
            return false;
        }

        return true;
    }

    private void cleanUp() {
        this.textWeightTrailer.setText("");
        this.textLicenseTrailer.setText("");
        this.DDTrailer.setValue(null);
    }

}
