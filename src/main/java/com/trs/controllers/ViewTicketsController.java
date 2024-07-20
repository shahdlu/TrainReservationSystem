package com.trs.controllers;

import com.trs.api.managers.TicketManager;
import com.trs.api.managers.TrainManager;
import com.trs.modules.Train;
import com.trs.modules.tickets.Ticket;
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

public class ViewTicketsController {
    static Ticket selectedTicket;
    static boolean editTrigger;
    static boolean adminTrigger;
   public static Train selectedTrain;
    static boolean trainTrigger;
    @FXML
    public Label PriceLabel;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TableView ticketTable;
    @FXML
    private TableColumn ticketIDColumn;
    @FXML
    private Label trainNumberLabel;
    @FXML
    private Label reservationDateLabel;
    @FXML
    private Label degreeLabel;

    @FXML
    private Label FareLabel;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button backButton;

   public ViewTicketsController() {
        super();
        adminTrigger = LoginController.IsAdmin();
        trainTrigger = ViewTrainController.IsTrain();
    }
    public static void setTrainNumber(Train train) {
        selectedTrain = train;
    }

    @FXML
    void backHandle(ActionEvent event) throws IOException {
       if(LoginController.IsAdmin()) {
           navigateTo(event, "/com/trs/forms/AdminActionPage.fxml");
           return;

       }
           navigateTo(event, "/com/trs/forms/OfficerActionPage.fxml");
    }

    @FXML
    void deleteHandle(ActionEvent event) throws SQLException {
        if (ticketTable.getSelectionModel().getSelectedItem() == null) {
            Alert err = new Alert(Alert.AlertType.ERROR, "Please select a ticket to delete", ButtonType.OK);
            err.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this ticket?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            try {
                TicketManager.removeTicket((Ticket) ticketTable.getSelectionModel().getSelectedItem());
                ticketTable.getItems().remove(ticketTable.getSelectionModel().getSelectedItem());
                Alert info = new Alert(Alert.AlertType.INFORMATION, "ticket deleted successfully", ButtonType.OK);
                clearLabels();
                info.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void editHandle(ActionEvent event) throws IOException {
        if (ticketTable.getSelectionModel().getSelectedItem() == null) {
            Alert err = new Alert(Alert.AlertType.ERROR, "Please select a ticket to edit", ButtonType.OK);
            err.showAndWait();
            return;
        }
        editTrigger = true;
        selectedTicket = (Ticket) ticketTable.getSelectionModel().getSelectedItem();
        navigateTo(event, "/com/trs/forms/ManageTickets.fxml");


    }

    @FXML
    void newHandle(ActionEvent event) throws IOException {
        navigateTo(event, "/com/trs/forms/ReserveTicket.fxml");
    }

    void populateTable() {
        try {
            if (trainTrigger) {
                ticketTable.getItems().addAll(selectedTrain.getAllTrainTickets());
                return;
            }
            ArrayList<Ticket> tickets = (ArrayList<Ticket>) TicketManager.getAllTickets();
                ticketTable.getItems().addAll(tickets);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void prepareColumns() {
        ticketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketNumber"));

    }

    void prepareTable() {
        ticketTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedTicket = (Ticket) newSelection;
                trainNumberLabel.setText(selectedTicket.getTrainNumber() + "");
                reservationDateLabel.setText(selectedTicket.getReservationDate().toString());
                switch (selectedTicket.getTicketClass()) {
                    case 1 -> degreeLabel.setText("First Class");
                    case 2 -> degreeLabel.setText("Second Class");
                    case 3 -> degreeLabel.setText("Third Class");
                }
                PriceLabel.setText(String.valueOf(selectedTicket.getFare()));
            }
        });
    }

    void clearLabels() {
        trainNumberLabel.setText("");
        reservationDateLabel.setText("");
        degreeLabel.setText("");
        PriceLabel.setText("");
    }

    void setPrivileges() {
        if (!LoginController.IsAdmin()) {
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }

    @FXML
    void initialize() {
        isInitialized();
        prepareColumns();
        populateTable();
        prepareTable();
        clearLabels();
        setPrivileges();
    }

    void isInitialized() {
        assert ticketTable != null : "fx:id=\"ticketTable\" was not injected: check your FXML file 'ViewTickets.fxml'.";
        assert ticketIDColumn != null : "fx:id=\"ticketIDColumn\" was not injected: check your FXML file 'ViewTickets.fxml'.";
        assert trainNumberLabel != null : "fx:id=\"trainNumberLabel\" was not injected: check your FXML file 'ViewTickets.fxml'.";
        assert reservationDateLabel != null : "fx:id=\"reservationDateLabel\" was not injected: check your FXML file 'ViewTickets.fxml'.";
        assert degreeLabel != null : "fx:id=\"degreeLabel\" was not injected: check your FXML file 'ViewTickets.fxml'.";
        assert FareLabel != null : "fx:id=\"FareLabel\" was not injected: check your FXML file 'ViewTickets.fxml'.";
        assert newButton != null : "fx:id=\"newButton\" was not injected: check your FXML file 'ViewTickets.fxml'.";
        assert editButton != null : "fx:id=\"editButton\" was not injected: check your FXML file 'ViewTickets.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'ViewTickets.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'ViewTickets.fxml'.";
    }
}
