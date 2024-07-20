package com.trs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OfficerActionController extends FormNavigator implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button viewTrainButton;
    @FXML
    private Button addTicketButton;
    @FXML
    private Button ExitButton;

    public OfficerActionController() {
        super();
    }

    @FXML
    void addTicketHandle(ActionEvent event) throws IOException {
        ReserveTicketController.reserveTrigger = false;
        navigateTo(event, "/com/trs/forms/ReserveTicket.fxml");
    }

    @FXML
    void exitHandle(ActionEvent event) throws IOException {
        navigateTo(event, "/com/trs/forms/Login.fxml");

    }

    @FXML
    void viewTrainHandle(ActionEvent event) throws IOException {
        navigateTo(event, "/com/trs/forms/ViewTrain.fxml");
    }

    @FXML
    void isInitialized() {
        assert viewTrainButton != null : "fx:id=\"viewTrainButton\" was not injected: check your FXML file 'OfficerActionPage.fxml'.";
        assert addTicketButton != null : "fx:id=\"addTicketButton\" was not injected: check your FXML file 'OfficerActionPage.fxml'.";
        assert ExitButton != null : "fx:id=\"ExitButton\" was not injected: check your FXML file 'OfficerActionPage.fxml'.";

    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isInitialized();
    }
}
