package group4.heidenliquids.order;

import dao.AbstractDAOFactory;
import dao.DAO;
import dao.DAOException;
import entities.Order;
import group4.heidenliquids.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderHomeController implements Initializable {

    public AnchorPane anchorOrders;
    public Button btNewOrder;

    private final Callback<Class<?>, Object> controllerFactory;

    AbstractDAOFactory factory;
    @FXML
    private ListView<Order> listOrders;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public OrderHomeController(AbstractDAOFactory factory, Callback<Class<?>, Object> controllerFactory) {
        this.factory = factory;
        this.controllerFactory = controllerFactory;
    }

    public void handlerNewOrder(ActionEvent actionEvent) {
        Parent root;
        Window parent = this.anchorOrders.getParent().getScene().getWindow();
        try {

            URL url = getClass().getResource("/fxml/order/order.fxml");

            FXMLLoader loader = new FXMLLoader(url);

            loader.setControllerFactory(this.controllerFactory);

            root = loader.load();


            Stage orderStage = new Stage();
            orderStage.initModality(Modality.APPLICATION_MODAL);

            Scene orderScene = new Scene(root);
            orderStage.setScene(orderScene);
            orderStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handlerRemoveSelected(ActionEvent actionEvent) {
        Order selected = this.listOrders.getSelectionModel().getSelectedItem();

        if (selected != null) {
            DAO<String, Order> dao = this.factory.createDao(Order.class);
            try {
                dao.delete(selected);
                this.listOrders.getSelectionModel().clearSelection();
                this.handlerRefreshOrders(actionEvent);
            } catch (DAOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Something went wrong.");
                alert.setContentText("Could not delete the selected order. Maybe no connection could be established...");
                alert.show();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No order has been selected...");
            alert.setContentText("Please select an order, before you want to delete it.");
            alert.show();
        }

    }

    @FXML
    private void handlerRefreshOrders(ActionEvent actionEvent) {
        DAO<String, Order> dao = this.factory.createDao(Order.class);
        ObservableList<Order> orders = FXCollections.observableArrayList();
        orders.addAll(dao.getAll());
        this.listOrders.setItems(orders);
    }
}
