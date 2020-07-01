/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenliquids.truck;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.PG.connection.PgJDBC;
import entities.Truck;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TextField currentWeightLoaded;
    @FXML
    private RadioButton availableButton;
    @FXML
    private RadioButton notAvailableButton;
    private Label result;
    @FXML
    private Label resultRed;
    @FXML
    private Label resultGreen;

    private ToggleGroup group;

    private Connection conn;
    @FXML
    ObservableList<Truck> items;
    @FXML
    private TableView<Truck> truckTable;
    @FXML
    private TableColumn<?, ?> LicenseCol;
    @FXML
    private TableColumn<?, ?> TypeCol;
    @FXML
    private TableColumn<?, ?> StatusCol;
    @FXML
    private TableColumn<?, ?> CurWeightCol;
    @FXML
    private TableColumn<?, ?> MWeightCol;
    @FXML
    private TableColumn<?, ?> MTowCol;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO THIS METHOD IS MARKED AS DEPRECATED. NO DATABASE CONNECTION IN THE FRONTEND! ~ Jonas
//        try {
//            this.conn = Connect.createConnection();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

        this.group = new ToggleGroup();

        availableButton.setToggleGroup(group);
        availableButton.setSelected(true);

        notAvailableButton.setToggleGroup(group);

        LicenseCol.setCellValueFactory(new PropertyValueFactory<>("LicenseNumber"));
        TypeCol.setCellValueFactory(new PropertyValueFactory<>("TypeOfTruck"));
        StatusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        CurWeightCol.setCellValueFactory(new PropertyValueFactory<>("CurrentWeightLoaded"));
        MWeightCol.setCellValueFactory(new PropertyValueFactory<>("MaxWeight"));
        MTowCol.setCellValueFactory(new PropertyValueFactory<>("MaxTowingWeight"));

//        this.listTrucks();
    }

    @FXML
    private void handlerCancel(ActionEvent event) {

        Platform.exit();
        System.exit(1);
    }

    @FXML
    private void handlerRegisterTruck(ActionEvent event) {

        if (this.licenseNumber.getText().isEmpty() || this.maximumTowingWeight.getText().isEmpty() || this.currentWeightLoaded.getText().isEmpty()
                || this.maximumWeight.getText().isEmpty() || this.typeOfTruck.getText().isEmpty()) {

            resultGreen.setText("");
            resultRed.setText("! Please enter all fields !");

        } else if (!this.maximumTowingWeight.getText().matches("[0-9]*\\.?[0-9]*")
                || !this.maximumWeight.getText().matches("[0-9]*\\.?[0-9]*")
                || !this.currentWeightLoaded.getText().matches("[0-9]*\\.?[0-9]*")) {

            this.resultGreen.setText("");
            this.resultRed.setText("! Please enter validate information !");

        } else {

            this.resultRed.setText("");

            String licenseNr = this.licenseNumber.getText();
            double maxTowingWeight = Double.parseDouble(this.maximumTowingWeight.getText());
            double maxWeight = Double.parseDouble(this.maximumWeight.getText());
            double currentWeighLoaded = Double.parseDouble(this.currentWeightLoaded.getText());
            String typeOfTruck = this.typeOfTruck.getText();
            boolean status = false;

            resultGreen.setText("! Your truck has been registered successfully !");

            if (this.group.getSelectedToggle() == this.availableButton) {
                status = true;
            }

            Truck truck = new Truck(licenseNr, typeOfTruck, status, currentWeighLoaded, maxWeight, maxTowingWeight);

            System.out.println(truck.toString());

            String query = "insert into trucks\n"
                    + "values ('" + truck.getLicenseNumber() + "', '" + truck.getTypeOfTruck() + "' , '" + truck.isStatus() + "' , '"
                    + truck.getCurrentWeightLoaded() + "' , '" + truck.getMaxWeight() + "' , '" + truck.getMaxTowingWeight() + "' );";

            ResultSet result = PgJDBC.doQuery(this.conn, query);
        }
    }

    private void listTrucks() {
        this.items = FXCollections.observableArrayList();

        String query = "select * from trucks;";

        try {
            ResultSet result = PgJDBC.doQuery(conn, query);

            if (result != null) {
                while (result.next()) {
                    this.items.add(new Truck(
                            result.getString("licence_number"),
                            result.getString("type_of_truck"),
                            result.getBoolean("status"),
                            result.getDouble("current_weight_loaded"),
                            result.getDouble("max_weight"),
                            result.getDouble("max_towing_weight")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.truckTable.setItems(items);
    }
}
