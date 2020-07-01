/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenliquids.executeOrder;

import dao.AbstractDAOFactory;
import dao.DAO;
import entities.Action;
import entities.ExecuteOrder;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import group4.heidenliquids.util.PopupLoader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author rajinder
 */
public class ExecuteOrderController implements Initializable {

    public TableView<ExecuteOrder> ExecuteOrderTable;

    public TableColumn DriverIdCol;
    public TableColumn TrailerLicenceNoCol;
    public TableColumn TruckLicenceNoCol;
    public TableColumn OrderIdCol;
    public TableColumn NameCol;
    public TableColumn IsHazardousCol;
    public TableColumn IsUnloadingCol;
    public TableColumn CountryCol;
    public TableColumn StreetCol;
    public TableColumn NumberCol;
    public TableColumn ZipCol;
    public TableColumn ActionIdCol;

    public Button closeButton;
    public Button createReceiptButton;


    //    ExecuteOrderBusiness businessLogic;
    AbstractDAOFactory factory;
    /**
     * Initializes the controller class.
     *
     * @param
     * @param
     */

    public ExecuteOrderController(AbstractDAOFactory factory) {
        this.factory = factory;
    }

    void initializeTable() {
        DriverIdCol.setCellValueFactory(new PropertyValueFactory<>("driverid"));
        OrderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TrailerLicenceNoCol.setCellValueFactory(new PropertyValueFactory<>("trailerlicenceno"));
        TruckLicenceNoCol.setCellValueFactory(new PropertyValueFactory<>("trucklicenceno"));
        IsHazardousCol.setCellValueFactory(new PropertyValueFactory<>("ishazardous"));
        IsUnloadingCol.setCellValueFactory(new PropertyValueFactory<>("isunloading"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        CountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        StreetCol.setCellValueFactory(new PropertyValueFactory<>("street"));
        NumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        ZipCol.setCellValueFactory(new PropertyValueFactory<>("zip"));
        ActionIdCol.setCellValueFactory(new PropertyValueFactory<>("actionid"));

        String driverID = "642000";
        DAO<String, ExecuteOrder> dao = factory.createDao(ExecuteOrder.class);
//        List<ExecuteOrder> executeOrders = dao.getAll().forEach();

//        this.ExecuteOrderTable.setItems(FXCollections.observableArrayList(dao.getAll()));
    }

    @FXML
    private void handlerCancel(ActionEvent event) {

        Platform.exit();
        System.exit(1);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        initializeTable();
    }

    @FXML
    public void handlerReceipt() {
        ExecuteOrder current = this.ExecuteOrderTable.getSelectionModel().getSelectedItem();
        System.out.println("Current: " + current);

        DAO<String, Action> actionDAO = factory.createDao(Action.class);
        Optional<Action> o = actionDAO.get(current.getActionid());
        System.out.println("Action id: " + current.getActionid());
        if (o.isPresent()) {
            Action action = o.get();
            this.loadReceiptView(action);
        }
    }

    private void loadReceiptView(Action action) {

        Callback<Class<?>, Object> receiptFactory = (Class<?> c) -> new ReceiptController(this.factory, action);
        PopupLoader loader = new PopupLoader();
        try {
            loader.load("/fxml/createReceipt.fxml", this.ExecuteOrderTable.getParent().getScene().getWindow(),
                    receiptFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
