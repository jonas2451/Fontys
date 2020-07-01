/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4.heidenliquids.invoice;

import dao.AbstractDAOFactory;
import dao.DAO;
import dao.util.GUID;
import entities.Invoice;
import entities.Order;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author sajee
 */
public class InvoiceController implements Initializable {

    
    @FXML
    private DatePicker invoiceDate;
    @FXML
    private Label lbInvoiceNr;
    @FXML
    private Button btMakeInvoice;
    @FXML
    private SplitPane anchorInvoice;
    @FXML
    private Button btPayInvoice;
    @FXML
    private TextField tfPayInvoice;
    @FXML
    private Button btSearchOrders;
    @FXML
    private ListView<Order> listOrders;
    @FXML
    private TextField tfSearchOrder;
    @FXML
    private Label lbError;
    @FXML
    private Label lbResult;
    
    AbstractDAOFactory factory;
    
    Callback<Class<?>, Object> controllerFactory;
    
    Invoice invoice;
    
    Order orderSelected;
    
    String orderid;
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    public void InvoiceController(AbstractDAOFactory factory){
        this.factory = factory;
    }
    
    public InvoiceController(AbstractDAOFactory factory, Callback<Class<?>, Object> controllerFactory) {
        this.factory = factory;
        this.controllerFactory = controllerFactory;
    }

    @FXML
    private void handlerMakeInvoice(ActionEvent event) {
        this.lbInvoiceNr.setText(GUID.generate());
        boolean paidstatus = false;
        
        if(this.tfPayInvoice.getText().equals("currently unpaid"))
            paidstatus = false;
        else
            paidstatus = true;
        
        Invoice unchecked = new Invoice(this.lbInvoiceNr.getText(), this.invoiceDate.getValue(), 
                paidstatus, this.orderid);
        
        if(this.validate(unchecked)){
            this.invoice = unchecked;
            
            DAO<String, Invoice> dao = this.factory.createDao(Invoice.class);
            dao.save(this.invoice);
        }

        
    }

    @FXML
    private void handlerPayInvoice(ActionEvent event) {
        this.tfPayInvoice.setText("Paid");
    }
    

    @FXML
    private void handlerSearchOrders(ActionEvent event) {
        ObservableList<Order> orders ;//= FXCollections.observableArrayList();
        
        DAO<String, Order> dao = this.factory.createDao(Order.class);
        
        if(this.tfSearchOrder.getText().isEmpty()){
            orders = FXCollections.observableArrayList();
            orders.addAll(dao.getAll());
        }
        else{
            orders = FXCollections.observableArrayList();
            Order result= dao.get(tfSearchOrder.getText()).get();
            orders.addAll(result);
        }
        
        
        this.listOrders.setItems(orders);
    }
    
    

    @FXML
    private void handlerListOrders(MouseEvent event) {
        this.orderSelected = (Order) this.listOrders.getSelectionModel().getSelectedItem();
        this.orderid = this.orderSelected.getId();
    }
    
    private boolean validate(Invoice invoice){
        
        if( invoice.getInvoiceDate() != null && invoice.getInvoiceDate().isAfter(LocalDate.now()) ){
            if(this.orderSelected != null){
                this.lbError.setText("");
                this.lbResult.setText("The invoice has been made");
                return true;
            }
            else{
                this.lbResult.setText("");
                this.lbError.setText("! Please select an Order");
                return false;
            }
        }
        
        else{
            this.lbResult.setText("");
            this.lbError.setText("! Please fill in the valid Invoice Date");
            return false;
        }
    }

    

    
    
}
