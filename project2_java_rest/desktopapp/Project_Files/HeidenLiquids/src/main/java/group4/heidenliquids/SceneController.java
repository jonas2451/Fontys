package group4.heidenliquids;

import group4.heidenliquids.address.AddressController;
import group4.heidenliquids.customer.BlockCustomerController;
import group4.heidenliquids.driver.RegisterDriverController;
import group4.heidenliquids.executeOrder.ExecuteOrderController;
import group4.heidenliquids.orderProduct.OrderProductController;
import group4.heidenliquids.customer.RegisterCustomerController;
import group4.heidenliquids.trailer.RegisterTrailerController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable {

    @FXML
    AnchorPane anchorCustomer;
    @FXML
    AnchorPane anchorRegisterCustomer;
    @FXML
    AnchorPane anchorDriver;
    @FXML
    Pane anchorTruck;
    @FXML
    AnchorPane anchorTrailer;
    @FXML
    AnchorPane anchorAddress;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
