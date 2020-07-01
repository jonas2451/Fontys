package group4.heidenliquids.order;

import com.sun.org.apache.xpath.internal.operations.Or;
import dao.AbstractDAOFactory;
import dao.DAO;
import dao.util.GUID;
import entities.Action;
import entities.Customer;
import entities.Order;
import entities.OrderProduct;
import group4.heidenliquids.orderProduct.OrderProductController;
import group4.heidenliquids.util.PopupLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class OrderController implements Initializable {
    @FXML
    public Button btRefresh;
    @FXML
    private TextField textCustomer;
    @FXML
    public DatePicker dpDate;
    @FXML
    private ListView<OrderProduct> listProducts;
    @FXML
    private AnchorPane anchorPaneCustomer;
    @FXML
    private TextField textCustomerSearch;
    @FXML
    private ListView<Customer> listCustomer;
    @FXML
    private Label lbErrors;

    Customer customerSelected;

    AbstractDAOFactory factory;

    Callback<Class<?>, Object> controllerFactory;

    Order currentOrder;

    Consumer<OrderProduct> consumer;
    @FXML
    private Button btEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public OrderController(AbstractDAOFactory factory, Callback<Class<?>, Object> controllerFactory) {
        this.factory = factory;
        this.controllerFactory = controllerFactory;
    }

    @FXML
    private void handlerSelectCustomer(ActionEvent actionEvent) {
        this.anchorPaneCustomer.setDisable(false);
    }

    @FXML
    private void handlerAdd(ActionEvent actionEvent){
        if (this.customerSelected != null) {
            if (this.currentOrder == null) {
                Order uncheckedOrder = new Order(GUID.generate(), dpDate.getValue(), this.customerSelected.getId());
                if (this.validateOrder(uncheckedOrder)) {
                    currentOrder = uncheckedOrder;
                    this.lbErrors.setText("");
                    this.loadOrderProductView(
                            (Class<?> c) -> new OrderProductController(this.factory, this.consumer = orderProduct -> this.currentOrder.addOrderProduct(orderProduct))
                    );
                }
            } else {
                this.loadOrderProductView(
                        (Class<?> c) -> new OrderProductController(this.factory, this.consumer = orderProduct -> this.currentOrder.addOrderProduct(orderProduct))
                );
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No customer selected!");
            alert.setContentText("Please select a customer before you proceed!");
            alert.show();
        }
    }

    @FXML
    private void handlerRemove(ActionEvent actionEvent) {
        if (this.listProducts.getSelectionModel().getSelectedItem() != null) {

            OrderProduct selected = this.listProducts.getSelectionModel().getSelectedItem();

            Collection<OrderProduct> products = this.currentOrder.getOrderProducts();
            if (products.contains(selected)) {
                products.remove(selected);
                this.refreshHandler(actionEvent);
            }
        }
    }

    /**
     * Fills the listview "listCustomer" with the search results
     * from the search entered into the "textCustomerSearch" search bar.
     *
     * @param actionEvent
     */
    @FXML
    private void handlerSearch(ActionEvent actionEvent) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        DAO<String, Customer> dao = this.factory.createDao(Customer.class);

        customers.addAll(dao.getByColumnValue("companyname", textCustomerSearch.getText()));

        this.listCustomer.setItems(customers);
    }

    @FXML
    private void handlerSubmitCustomer(ActionEvent actionEvent) {

        if (this.listCustomer.getSelectionModel().getSelectedItem() != null) {

            this.customerSelected = (Customer) this.listCustomer.getSelectionModel().getSelectedItem();

            this.textCustomerSearch.setText("");

            this.textCustomer.setText(this.customerSelected.getCompanyName());

            this.listCustomer.setItems(FXCollections.observableArrayList());

            this.anchorPaneCustomer.setDisable(true);
        }
    }

    @FXML
    private void handlerSubmit(ActionEvent actionEvent) {

        if (this.currentOrder != null && !this.currentOrder.getOrderProducts().isEmpty()) {

            this.currentOrder.setOrderDate(dpDate.getValue());

            //Put foreign key orderId in all orderProducts
            this.currentOrder.getOrderProducts().forEach(orderProduct -> orderProduct.setOrderid(this.currentOrder.getId()));
            System.out.println("Current Order: " + currentOrder);
            DAO<String, Order> dao = this.factory.createDao(Order.class);
            try {
                dao.save(this.currentOrder.validateOrder());
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Illegal arguments");
                alert.setContentText(ex.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No order products detected!");
            alert.setContentText("Please add at least one order product to your order!");
            alert.show();
        }
    }

    @FXML
    private void handlerCancel(ActionEvent actionEvent) {
        this.currentOrder = null;
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


    @FXML
    private void refreshHandler(ActionEvent actionEvent) {
        if (this.currentOrder == null) {

        }
        if (this.currentOrder != null && this.currentOrder.getOrderProducts() != null) {
            ObservableList<OrderProduct> list = FXCollections.observableArrayList(this.currentOrder.getOrderProducts());
            this.listProducts.setItems(list);
            System.out.println(this.currentOrder);
        }
    }


    @FXML
    private void handlerOrderList(MouseEvent mouseEvent) {

    }


    @FXML
    private void handlerCustomerList(MouseEvent mouseEvent) {
    }


    /**
     * Loads the orderProduct view
     */
    private void loadOrderProductView(Callback<Class<?>, Object> callback) {


        PopupLoader loader = new PopupLoader();
        try {
            loader.load("/fxml/order/orderProduct.fxml", this.anchorPaneCustomer.getParent().getScene().getWindow(), callback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean validateOrder(Order order) {
        if (order != null &&
                order.getOrderDate() != null &&
                order.getOrderDate().isAfter(LocalDate.now()) &&
                        order.getOrderDate().isBefore(LocalDate.now().plusYears(1))) {
            this.lbErrors.setText("");
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Invalid oder date!");
        alert.setContentText("Please select a data that is not in the past and a date that is not more than one year in the future.");
        alert.show();
        return false;
    }

    @FXML
    private void handlerEdit(ActionEvent actionEvent) {
        OrderProduct product = this.listProducts.getSelectionModel().getSelectedItem();

        this.currentOrder.getOrderProducts().remove(product);

        Callback<Class<?>, Object> orderProductFactory = (Class<?> c) -> new OrderProductController(this.factory, this.consumer = orderProduct -> this.currentOrder.addOrderProduct(orderProduct), product);


        this.loadOrderProductView(orderProductFactory);
    }
}
