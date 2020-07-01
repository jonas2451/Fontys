package group4.heidenliquids.planning;

import dao.AbstractDAOFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class PlanningController implements Initializable {

    @FXML
    private ListView<?> lvDrivers;

    @FXML
    private Button btnMoveDriverOrder;

    @FXML
    private Button btnConfigureLocation;

    @FXML
    private ListView<?> lvProducts;

    @FXML
    private Button btnMoveProductOrder;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private TextField workOrderSearchTbx;

    @FXML
    private DatePicker dpStartDate;

    @FXML
    private DatePicker dpEndDate;

    @FXML
    private Button btnSearch;

    @FXML
    private ListView<?> lvWorkOrders;

    @FXML
    private Button editWorkOrderBtn;

    @FXML
    private Button addWorkOrderBtn;

    @FXML
    private Button removeWorkOrderBtn;

    @FXML
    private Button btnSearchOrder;

    @FXML
    private Label productId;

    @FXML
    private Label workOrderId;

    @FXML
    private Label productName;

    @FXML
    private Label driverId;

    @FXML
    private Label driverName;

    @FXML
    private Label trailerLicense;

    @FXML
    private Label truckLicense;

    @FXML
    private ListView<?> lvTrucks;

    @FXML
    private Button btnMoveTruckOrder;

    @FXML
    private ListView<?> lvTrailers;

    @FXML
    private Button btnMoveTrailerOrder;

    @FXML
    void AddWorkOrder(ActionEvent event) {

    }

    @FXML
    void EditWorkOrder(ActionEvent event) {

    }

    @FXML
    void RemoveWorkOrder(ActionEvent event) {

    }

    @FXML
    void configureLocation(ActionEvent event) {

    }

    @FXML
    void moveDriverOrder(ActionEvent event) {

    }

    @FXML
    void moveProductOrder(ActionEvent event) {

    }

    @FXML
    void moveTrailerOrder(ActionEvent event) {

    }

    @FXML
    void moveTruckOrder(ActionEvent event) {

    }

    @FXML
    void searchDates(ActionEvent event) {

    }

    @FXML
    void searchOrder(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public PlanningController(AbstractDAOFactory factory){

    }
}
