package group4.heidenliquids.address;

import java.net.URL;
import java.util.ResourceBundle;

import dao.AbstractDAOFactory;
import dao.DAO;
import dao.util.GUID;
import entities.Address;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen
 * {@code 423121@student.fontys.nl}
 */
public class AddressController implements Initializable {

    @FXML
    public TextField textCountry;
    @FXML
    public Label lbErrors;
    @FXML
    private TextField textCity;
    @FXML
    private TextField textZIP;
    @FXML
    private TextField textStreet;
    @FXML
    private TextField textNumber;
    @FXML
    private Button btSubmit;
    @FXML
    private Button btCancel;

    private AbstractDAOFactory factory;
    @FXML
    private AnchorPane anchorAddress;

    /**
     *
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public AddressController(AbstractDAOFactory factory) {
        this.factory = factory;
//        this.dao = business.getDao()[0];
    }

    /**
     * Makes the business logic create a new Address and save it into the database
     * @param event
     */
    @FXML
    private void handlerSubmit(ActionEvent event) {

        if (this.validate()) {
            DAO dao = this.factory.createDao(Address.class);
            dao.save(new Address(GUID.generate(), this.textCountry.getText(), this.textCity.getText(), this.textZIP.getText(), this.textStreet.getText(), this.textNumber.getText()));
            this.cleanUp();
        }
    }

    @FXML
    private void handlerCancel(ActionEvent event) {
        this.cleanUp();
    }

    /**
     *
     * clears the text areas in the view
     */
    private void cleanUp() {
        this.textCountry.setText("");
        this.textCity.setText("");
        this.textNumber.setText("");
        this.textStreet.setText("");
        this.textZIP.setText("");
        this.lbErrors.setText("");
    }

    /**
     *
     * validates whether all text fields have text in them
     *
     * @return boolean <true> if all fields have text in them </true> <false> if
     * at least one field is empty </false>
     */
    private boolean validate() {

        if (this.textCountry.getText().isEmpty() || this.textCity.getText().isEmpty()
                || this.textZIP.getText().isEmpty() || this.textStreet.getText().isEmpty()
                || this.textNumber.getText().isEmpty()) {
            this.lbErrors.setText("please fill in all fields!");
            return false;
        }

        return true;
    }
}
