package group4.heidenliquids.orderProduct;

import dao.AbstractDAOFactory;
import dao.DAO;
import dao.PG.connection.Connect;
import dao.util.GUID;
import entities.Action;
import entities.Address;
import entities.Order;
import entities.OrderProduct;
import exceptions.OrderProductWeightException;
import group4.heidenliquids.util.PopupLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class OrderProductController implements Initializable {
    @FXML
    private TextField textProductName;
    @FXML
    private TextField textTotalWeight;
    @FXML
    private CheckBox cdHazardous;
    @FXML
    private TextField textPrice;
    @FXML
    private TableView<Action> tableActions;
    @FXML
    private ListView<Address> listLocation;
    @FXML
    private TextField textLocation;
    @FXML
    private RadioButton rbLoading;
    @FXML
    private RadioButton rbUnloading;
    @FXML
    private TextField textWeight;
    @FXML
    private TextField textCountry;
    @FXML
    private TextField textZip;
    @FXML
    private TextField textCity;
    @FXML
    private TextField textStreet;
    @FXML
    private TableColumn<Action, Integer> trackingNo;
    @FXML
    private TableColumn<Action, Boolean> action;
    @FXML
    private TableColumn<Action, BigDecimal> expectedWeight;
    @FXML
    private TableColumn<Action, String> address;
    @FXML
    private AnchorPane anchorPaneParent;

    Callback<Class<?>, Object> controllerFactory;

    final ToggleGroup hazardousGroup = new ToggleGroup();

    Address currentAddress;

    OrderProduct orderProduct;

    Consumer<OrderProduct> consumer;

    AbstractDAOFactory factory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.makeButtonGroup();
        this.editInformation();
    }

    public OrderProductController(AbstractDAOFactory factory, Callback<Class<?>, Object> controllerFactory) {
        this.factory = factory;
        this.controllerFactory = controllerFactory;
    }

    public OrderProductController(AbstractDAOFactory factory, Consumer<OrderProduct> consumer, OrderProduct product) {
        this.consumer = consumer;
        this.factory = factory;
        this.orderProduct = product;
        System.out.println(orderProduct);
    }

    public OrderProductController(AbstractDAOFactory factory, Consumer<OrderProduct> consumer) {
        this.consumer = consumer;
        this.factory = factory;
    }

    @FXML
    private void handlerActionsTable(MouseEvent mouseEvent) {
    }

    @FXML
    private void handlerAddProduct(ActionEvent actionEvent) {
        ObservableList<Action> actions = this.tableActions.getItems();

        boolean hazardous = this.cdHazardous.isSelected();
        try {
            OrderProduct product = new OrderProduct(GUID.generate(), this.textProductName.getText(), hazardous, BigDecimal.valueOf(Double.parseDouble(this.textTotalWeight.getText())), BigDecimal
                    .valueOf(Double.parseDouble(textPrice.getText())), null);

            //put foreign key productId in all actions
            actions.forEach(a -> a.setProductId(product.getId()));

            ArrayList<Action> list = new ArrayList<>(actions);

            product.setActions(list);
            consumer.accept(product);

            //closes the popup
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Information missing");
            alert.setContentText("Please fill in all information. Use numbers only for the weight fields!");
            alert.show();
        }
    }

    @FXML
    private void handlerRemove(ActionEvent actionEvent) {
        ObservableList<Action> actionSelected, allActions;
        allActions = tableActions.getItems();
        actionSelected = tableActions.getSelectionModel().getSelectedItems();

        actionSelected.forEach(allActions::remove);
    }

    @FXML
    private void handlerRemoveAll(ActionEvent actionEvent) {
        //TODO
    }

    @FXML
    private void handlerLocationList(MouseEvent mouseEvent) {

        this.currentAddress = (Address) this.listLocation.getSelectionModel().getSelectedItem();

        if (this.validate()) {
            this.textCountry.setText(currentAddress.getCountry());
            this.textCity.setText(currentAddress.getCity());
            this.textStreet.setText(currentAddress.getStreet() + " " + currentAddress.getNumber());
            this.textZip.setText(currentAddress.getZip());
        }
    }

    @FXML
    /**
     *
     * displays the search results for addresss in the listLocations
     */
    private void handlerSearch(ActionEvent actionEvent) {
        System.out.println("Factory: " + factory);
        DAO<String, Address> dao = this.factory.createDao(Address.class);
        ObservableList<Address> addresses = FXCollections.observableArrayList();

        addresses.addAll(dao.getByColumnValue("street", this.textLocation.getText()));

        this.listLocation.setItems(addresses);
    }

    @FXML
    private void handlerAddAction(ActionEvent actionEvent) {

        if (this.currentAddress != null && !this.textWeight.getText().isEmpty()) {
            boolean isUnloading = false;

            if (this.hazardousGroup.getSelectedToggle() == this.rbUnloading) {
                isUnloading = true;
            }
            //create new Action from selected data -> pass it on into the table
            prepareTableView();

            this.addTableRow(new Action(GUID.generate(), 0, isUnloading, BigDecimal.valueOf(Double.parseDouble(this.textWeight.getText())), this.currentAddress));

            this.cleanUp();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Insufficient information given!");
            alert.setContentText("Please select an address and enter a date!");
            alert.show();
        }
    }

    @FXML
    private void handlerNewLocation(ActionEvent actionEvent) {
        PopupLoader loader = new PopupLoader();

        System.out.println("test" + anchorPaneParent.getParent().getScene().getWindow());

        try {
            loader.load("/fxml/address/address.fxml", anchorPaneParent.getParent().getScene().getWindow(), this.controllerFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //helper methods

    /**
     *
     * Creates a button group so only 'unloading' or 'loading' can be selected.
     */
    private void makeButtonGroup() {

        rbLoading.setSelected(true);
        rbLoading.setToggleGroup(hazardousGroup);

        rbUnloading.setToggleGroup(hazardousGroup);
    }

    private boolean validate() {                                                                                        //TODO
        return true;
    } //TODO

    /**
     *
     * Adds an action object to the table.
     * @param action object to be displayed in the table view
     */
    private void addTableRow(Action action){
        this.tableActions.getItems().add(action);
    }

    /**
     *
     * Binds the attributes of an action object (that is put into the table) to the table cells.
     * (this method invoked the getter-methods of those attributes, do not change or remove them!)
     */
    private void prepareTableView() {
        this.trackingNo.setCellValueFactory(new PropertyValueFactory<Action, Integer>("trackingNumber"));
        this.action.setCellValueFactory(new PropertyValueFactory<Action, Boolean>("isUnloading"));
        this.expectedWeight.setCellValueFactory(new PropertyValueFactory<Action, BigDecimal>("expectedWeight"));
        this.address.setCellValueFactory(new PropertyValueFactory<Action, String>("addressAsString"));
    }

    /**
     *
     * Clears input fields after an Action has been added.
     */
    private void cleanUp() {
        this.textLocation.clear();
        this.textZip.clear();
        this.textStreet.clear();
        this.textCity.clear();
        this.textCountry.clear();
        this.textWeight.clear();

        this.rbLoading.setSelected(true);

        this.currentAddress = null;
    }

    private void editInformation() {
        if (this.orderProduct != null) {
            this.prepareTableView();
            for (Action a : this.orderProduct.getActions()) {
                this.addTableRow(a);
            }
            this.textProductName.setText(orderProduct.getName());
            this.textTotalWeight.setText(orderProduct.getTotalWeight().toString());
            this.cdHazardous.setSelected(orderProduct.isHazardous());
            this.textPrice.setText(orderProduct.getPricePerTon().toString());
        }
    }
}
