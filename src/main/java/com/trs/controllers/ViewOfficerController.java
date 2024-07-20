package com.trs.controllers;

import com.trs.api.managers.OfficerManager;
import com.trs.modules.TicketingOfficer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.trs.controllers.FormNavigator.navigateTo;

public class ViewOfficerController {

    OfficerManager officerManager = new OfficerManager();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableView officerTable;
    @FXML
    private TableColumn IDColumn;
    @FXML
    private TableColumn usernameColumn;
    @FXML
    private TableColumn positionCoulmn;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label salaryLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Button backButton;
    public ViewOfficerController() {

        super();
    }

    @FXML
    void backHandle(ActionEvent event) throws IOException {
        navigateTo(event, "/com/trs/forms/AdminActionPage.fxml");
    }

    @FXML
    void deleteHandle(ActionEvent event) {
        if (lastNameLabel.getText().trim().equals("")) {
            Alert err = new Alert(Alert.AlertType.ERROR, "Please select an officer to delete", ButtonType.OK);
            err.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this officer?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            try {
                officerManager.deleteTicketingOfficer((TicketingOfficer) officerTable.getSelectionModel().getSelectedItem());
                officerTable.getItems().remove(officerTable.getSelectionModel().getSelectedItem());
                firstNameLabel.setText("");
                lastNameLabel.setText("");
                salaryLabel.setText("");
                phoneNumberLabel.setText("");
                addressLabel.setText("");
                Alert info = new Alert(Alert.AlertType.INFORMATION, "Officer deleted successfully", ButtonType.OK);
                info.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void editHandle(ActionEvent event) {
        if (lastNameLabel.getText().trim().equals("")) {
            Alert err = new Alert(Alert.AlertType.ERROR, "Please select an officer to edit", ButtonType.OK);
            err.showAndWait();
            return;
        }
        try {
            TicketingOfficer tf = (TicketingOfficer) officerTable.getSelectionModel().getSelectedItem();
            System.out.println("Officer selected: " + tf.getUsername());
            ManageOfficerController.setSelectedOfficer(tf);
            ManageOfficerController.setEditTrigger(true);

            navigateTo(event, "/com/trs/forms/ManageOfficer.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void newHandle(ActionEvent event) throws IOException {
        navigateTo(event, "/com/trs/forms/ManageOfficer.fxml");

    }

    void populateTable() {
        try {
            ArrayList<TicketingOfficer> officers = (ArrayList<TicketingOfficer>) officerManager.getAllTicketingOfficers();
            for (TicketingOfficer officer : officers) {
                officerTable.getItems().add(officer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void prepareColumns() {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        positionCoulmn.setCellValueFactory(new PropertyValueFactory<>("position"));
    }

    void prepareTable() {
        officerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                TicketingOfficer officer = (TicketingOfficer) newSelection;
                firstNameLabel.setText(officer.getFirstName());
                lastNameLabel.setText(officer.getLastName());
                salaryLabel.setText(String.valueOf(officer.getSalary()));
                phoneNumberLabel.setText(officer.getPhoneNumber());
                addressLabel.setText(officer.getAddress());
            }
        });
    }

    @FXML
    void initialize() throws SQLException {
        isInitialized();
        prepareColumns();
        populateTable();
        prepareTable();
    }

    private void isInitialized() {
        assert officerTable != null : "fx:id=\"officerTable\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert IDColumn != null : "fx:id=\"IDColumn\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert usernameColumn != null : "fx:id=\"usernameColumn\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert positionCoulmn != null : "fx:id=\"positionCoulmn\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert newButton != null : "fx:id=\"newButton\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert editButton != null : "fx:id=\"editButton\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert firstNameLabel != null : "fx:id=\"firstNameLabel\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert lastNameLabel != null : "fx:id=\"lastNameLabel\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert salaryLabel != null : "fx:id=\"salaryLabel\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert phoneNumberLabel != null : "fx:id=\"phoneNumberLabel\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert addressLabel != null : "fx:id=\"addressLabel\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'ViewOfficer.fxml'.";
    }
}
