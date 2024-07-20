package com.trs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminActionController extends FormNavigator implements Initializable {

    @FXML
    private Button manageTrainButton;
    @FXML
    private Button manageTicketButton;
    @FXML
    private Button manageOfficerButton;
    @FXML
    private Button exitButton;

    public AdminActionController() {
        super();
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        LoginController.setIsAdmin(false);
        navigateTo(event, "/com/trs/forms/Login.fxml");

    }

    @FXML
    void viewOfficer(ActionEvent event) throws IOException {
        navigateTo(event, "/com/trs/forms/ViewOfficer.fxml");


    }

    @FXML
    void viewTicket(ActionEvent event) throws IOException {
        ViewTicketsController.adminTrigger = true;
        navigateTo(event, "/com/trs/forms/ViewTickets.fxml");

    }

    @FXML
    void viewTrain(ActionEvent event) throws IOException {
        navigateTo(event, "/com/trs/forms/ViewTrain.fxml");

    }


    public void isInitialized() {
        assert manageTrainButton != null : "fx:id=\"manageTrainButton\" was not injected: check your FXML file 'AdminActionPage.fxml'.";
        assert manageTicketButton != null : "fx:id=\"manageTicketButton\" was not injected: check your FXML file 'AdminActionPage.fxml'.";
        assert manageOfficerButton != null : "fx:id=\"manageOfficerButton\" was not injected: check your FXML file 'AdminActionPage.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'AdminActionPage.fxml'.";

    }


    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isInitialized();

    }
}
