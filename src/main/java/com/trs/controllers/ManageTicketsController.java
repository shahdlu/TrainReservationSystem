package com.trs.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import static com.trs.controllers.ManageTrainController.showErrorMessage;
import static com.trs.controllers.ManageTrainController.tryParse;

public class ManageTicketsController extends FormNavigator implements Initializable {
    public boolean isNumber() {
        if (  !(tryParse(totalPriceTextField.getText())&&
                tryParse(ticketNumberTextField.getText()))){
            showErrorMessage("Text setting failed");
            return false;
        }return true;
    }



    int numberOfTickets;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label label;
    @FXML
    private TextField trainNumberTextField;
    @FXML
    private TextField degreeTextField;
    @FXML
    private TextField totalPriceTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField ticketNumberTextField;
    @FXML
    private DatePicker reserveDateTimePicker;

    @FXML
    void cancelhandle(ActionEvent event) {

    }

    @FXML
    void editHandel(ActionEvent event) {

    }
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isInitialized();

    }

    void isInitialized() {
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'ManageTickets.fxml'.";
        assert trainNumberTextField != null : "fx:id=\"trainNumberTextField\" was not injected: check your FXML file 'ManageTickets.fxml'.";
        assert degreeTextField != null : "fx:id=\"degreeTextField\" was not injected: check your FXML file 'ManageTickets.fxml'.";
        assert totalPriceTextField != null : "fx:id=\"totalPriceTextField\" was not injected: check your FXML file 'ManageTickets.fxml'.";
        assert editButton != null : "fx:id=\"editButton\" was not injected: check your FXML file 'ManageTickets.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'ManageTickets.fxml'.";
        assert ticketNumberTextField != null : "fx:id=\"ticketNumberTextField\" was not injected: check your FXML file 'ManageTickets.fxml'.";
        assert reserveDateTimePicker != null : "fx:id=\"reserveDateTimePicker\" was not injected: check your FXML file 'ManageTickets.fxml'.";

    }

}
