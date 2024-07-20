package com.trs.controllers;

import com.trs.api.managers.OfficerManager;
import com.trs.modules.SystemAdmin;
import com.trs.modules.TicketingOfficer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.trs.controllers.FormNavigator.navigateTo;
import static com.trs.controllers.ManageTrainController.showErrorMessage;
import static com.trs.controllers.ManageTrainController.tryParse;

public class ManageOfficerController extends FormNavigator implements Initializable {

    public static boolean editTrigger;
    public static TicketingOfficer selectedOfficer;
    @FXML
    public ComboBox positionDropDown;
    @FXML
    public TextField passwordTextField;
    @FXML
    public TextField usernameTextField;
    String status;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField salaryTextField;
    @FXML
    private TextField IDTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;

    public ManageOfficerController() {
        super();
    }

    public static void setEditTrigger(boolean trigger) {
        editTrigger = trigger;
    }

    public static void setSelectedOfficer(TicketingOfficer officer) {
        ManageOfficerController.selectedOfficer = officer;
    }

    private void setValues() {
        firstNameTextField.setText(selectedOfficer.getFirstName());
        lastNameTextField.setText(selectedOfficer.getLastName());
        phoneNumberTextField.setText(selectedOfficer.getPhoneNumber());
        salaryTextField.setText(String.valueOf(selectedOfficer.getSalary()));
        addressTextField.setText(selectedOfficer.getAddress());
        usernameTextField.setText(selectedOfficer.getUsername());
        passwordTextField.setText(selectedOfficer.getPassword());
        IDTextField.setText(String.valueOf(selectedOfficer.getId()));
    }

    private TicketingOfficer getValues(TicketingOfficer officer) {
        officer.setFirstName(firstNameTextField.getText());
        officer.setLastName(lastNameTextField.getText());
        officer.setPhoneNumber(phoneNumberTextField.getText());
        officer.setSalary(Integer.parseInt(salaryTextField.getText()));
        officer.setAddress(addressTextField.getText());
        officer.setUsername(usernameTextField.getText());
        officer.setPassword(passwordTextField.getText());
        officer.setId(Integer.parseInt(IDTextField.getText()));
        return officer;
    }

    private boolean checkValues() {
        return !firstNameTextField.getText().isEmpty() &&
                !lastNameTextField.getText().isEmpty() &&
                !phoneNumberTextField.getText().isEmpty() &&
                !salaryTextField.getText().isEmpty() &&
                !addressTextField.getText().isEmpty() &&
                !usernameTextField.getText().isEmpty() &&
                !passwordTextField.getText().isEmpty() &&
                !IDTextField.getText().isEmpty();
    }

    private String checkSuccess(TicketingOfficer officer) throws SQLException {
        if (editTrigger) {
            status = new SystemAdmin().updateOfficer(officer);
            return status;
        }
        status = new SystemAdmin().addOfficer(officer);
        return status;
    }

    public void updateOfficer() throws SQLException {
        if (checkValues()) {
            TicketingOfficer officer = getValues(selectedOfficer);
            String status = checkSuccess(officer);
            Alert alert;
            if (status.equals("Success")) {
                alert = new Alert(Alert.AlertType.INFORMATION, status);
                alert.setTitle("Success");
                alert.setHeaderText(status);
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Officer Update Failed");
                alert.setContentText(status);
            }
            alert.showAndWait();
        }
    }

    public void addOfficer(TicketingOfficer officer) throws SQLException {
        if (checkValues()) {
            String status = checkSuccess(getValues(officer));
            Alert alert;
            if (status.equals("Success")) {
                alert = new Alert(Alert.AlertType.INFORMATION, status);
                alert.setTitle("Success");
                alert.setHeaderText(status);
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Officer Add Failed");
                alert.setContentText(status);
            }
            alert.showAndWait();
        }
    }
    public boolean isNumber() {
        if (  !(tryParse(IDTextField.getText())&&
                tryParse(salaryTextField.getText())&&
                tryParse(phoneNumberTextField.getText()))) {
            return false;
        }
        return true;
    }
    public boolean isText() {
        return !tryParse(firstNameTextField.getText()) && !tryParse(lastNameTextField.getText());
    }
    public static void confirmMessage(String e){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setHeaderText("Successfully");
        alert.setContentText(e);
        alert.showAndWait();
    }
    @FXML
    void addHandle(ActionEvent event) throws SQLException {
        if (!isText() || !isNumber()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Text setting failed");
            alert.setContentText("Please fill all the text fields correctly");
            alert.showAndWait();
            return;
        }
        if (!editTrigger) {
            if (OfficerManager.IDExists(Integer.parseInt(IDTextField.getText()))) {
                showErrorMessage("ID already exists");
                return;
            }
            if (positionDropDown.getValue().equals("Ticketing Officer")) {
                addOfficer(new TicketingOfficer());
                return;
            }
            addOfficer(new SystemAdmin());
            return;
        }
        updateOfficer();
        clearFields();
    }

    @FXML

    void backHandle(ActionEvent event) throws IOException {
        navigateTo(event, "/com/trs/forms/ViewOfficer.fxml");
        setEditTrigger(false);
    }

    void clearFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        phoneNumberTextField.clear();
        salaryTextField.clear();
        addressTextField.clear();
        usernameTextField.clear();
        passwordTextField.clear();
        IDTextField.clear();
    }

    void isInitialized() {
        assert firstNameTextField != null : "fx:id=\"firstNameTextField\" was not injected: check your FXML file 'ManageOfficer.fxml'.";
        assert lastNameTextField != null : "fx:id=\"lastNameTextField\" was not injected: check your FXML file 'ManageOfficer.fxml'.";
        assert phoneNumberTextField != null : "fx:id=\"phoneNumberTextField\" was not injected: check your FXML file 'ManageOfficer.fxml'.";
        assert salaryTextField != null : "fx:id=\"salaryTextField\" was not injected: check your FXML file 'ManageOfficer.fxml'.";
        assert IDTextField != null : "fx:id=\"IDTextField\" was not injected: check your FXML file 'ManageOfficer.fxml'.";
        assert addressTextField != null : "fx:id=\"addressTextField\" was not injected: check your FXML file 'ManageOfficer.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'ManageOfficer.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'ManageOfficer.fxml'.";
    }

    public void setOfficerPosition() {
        if (selectedOfficer instanceof SystemAdmin)
            positionDropDown.getSelectionModel().select(1);
        else
            positionDropDown.getSelectionModel().select(0);

    }
    public void disableIDTextField() {
        IDTextField.setDisable(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isInitialized();
        positionDropDown.getItems().addAll(new String[]{"Ticketing Officer", "System Admin"});
        if (editTrigger) {
            setValues();
            setOfficerPosition();
            disableIDTextField();
        }
        positionDropDown.getSelectionModel().select(0);
    }
}

