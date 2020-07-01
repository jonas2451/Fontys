package group4.heidenliquids.executeOrder;

import dao.AbstractDAOFactory;
import dao.DAO;
import entities.Action;
import entities.Receipt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReceiptController extends AbstractDAOFactory implements Initializable {

    public RadioButton unloadingButton;
    public RadioButton loadingButton;
    public TextField receiptNumberInput;
    public TextField actualWeightInput;
    public DatePicker DateInput;


    private ToggleGroup group;

    AbstractDAOFactory factory;
    Action action;
    Receipt receipt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.group = new ToggleGroup();

        unloadingButton.setToggleGroup(group);
        unloadingButton.setSelected(true);

        loadingButton.setToggleGroup(group);

    }

    public ReceiptController(AbstractDAOFactory factory, Action action) {
        this.action = action;
        this.factory = factory;
    }


    @FXML
    public void submitReceipt() {
        DAO<String, Receipt> dao = factory.createDao(Receipt.class);
        dao.save(new Receipt(receiptNumberInput.getText(), this.unloadingButton.isSelected(),
                BigDecimal.valueOf(Double.parseDouble(this.actualWeightInput.getText())), LocalDate.now()));
    }


    public void cancelReceipt(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
