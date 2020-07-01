/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenliquids.driver;

import java.net.URL;

//import com.sun.org.glassfish.external.statistics.annotations.Reset;
import entities.Driver;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

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
    @FXML
    private TableView<Driver> tableDriverLicenses;

    //Should maybe add this somewhere else, but putting it here for now
    private ObservableList<Driver> driversList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        TableColumn name = new TableColumn("name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn license = new TableColumn("license");
        license.setCellValueFactory(new PropertyValueFactory<>("license"));

        license.setPrefWidth(this.tableDriverLicenses.getPrefWidth()/2);
        name.setPrefWidth(this.tableDriverLicenses.getPrefWidth()/2);

        this.tableDriverLicenses.getColumns().addAll(name, license);

        //this should become a db call in the future

        Driver drive = new Driver("David", "Winters", "DavidBWinters@rhyta.com",
                           LocalDate.of(1956, Month.JANUARY, 1), "Fontys Street 9", LocalDate.now(), "1", "1F");
        Driver drive2 = new Driver("Mitch", "Mooych", "Mitch@mooych.co.uk",
                LocalDate.of(1998, Month.JANUARY, 23), "Random Street 73", LocalDate.now(), "2", "2F");
        driversList = FXCollections.observableArrayList();
        driversList.add(drive);
        driversList.add(drive2);
        ResetTable();
    }

    @FXML
    private void registerDriver(ActionEvent event) {
        Driver toAdd = new Driver(driverFirstName.getText(), driverLastName.getText(), driverEmail.getText(), dateOfBirth.getValue(), driverAddress.getText(),
                dateOfJoining.getValue(), driverAccountNumber.getText(), driverLicense.getText());
        driversList.add(toAdd);
        ResetTable();
        System.out.println("Driver has been registered");
    }

    private void ResetTable(){
        this.tableDriverLicenses.setItems(driversList);
    }
}
