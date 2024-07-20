package com.trs.controllers;

import com.trs.api.managers.TrainManager;
import com.trs.modules.Train;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewTrainController extends FormNavigator implements Initializable {


    public static Train selectedTrain;
    public static boolean adminTrigger;
    @FXML
    public Button addTrainBtn;
    @FXML
    public Button editTrainBtn;
    @FXML
    public Button deleteTrainBtn;
    @FXML
    public Label currentCapacityLabel;
    public Button reserveTicket;
    @FXML
    private Label arrivalStationL;
    @FXML
    private Label arrivalTimeL;
    @FXML
    private Button backBtn;
    @FXML
    private Label departureStationL;
    @FXML
    private Label departureTimeL;
    @FXML
    private Label maximumCapacityL;
    @FXML
    private Label priceL;
    @FXML
    private TableColumn trainNumberColumn;
    @FXML
    private TableView trainTable;
    @FXML
    private TableColumn trainTypeColumn;
    public ViewTrainController() {
        super();
    }
  private static  boolean isTrain;
    public static boolean IsTrain() {

        return isTrain;
    }

    @FXML
    public void viewSelectActionPage(ActionEvent actionEvent) throws IOException {
        if (!LoginController.IsAdmin()) {
            navigateTo(actionEvent, "/com/trs/forms/OfficerActionPage.fxml");
            return;
        }
        navigateTo(actionEvent, "/com/trs/forms/AdminActionPage.fxml");
    }
    @FXML
    public void ViewTicketsTrainPage(ActionEvent actionEvent) throws IOException {
        if (trainTable.getSelectionModel().getSelectedItem() != null) {
            ViewTicketsController.selectedTrain = (Train) trainTable.getSelectionModel().getSelectedItem();
            ViewTicketsController.setTrainNumber(selectedTrain);
            isTrain = true;
            navigateTo(actionEvent, "/com/trs/forms/ViewTickets.fxml");
        }
        new Alert(Alert.AlertType.ERROR, "Please select a train first").showAndWait();
    }

    @FXML
    void deleteSelectedTrain(ActionEvent event) {
        if (trainTable.getSelectionModel().getSelectedItem() == null) {
            Alert err = new Alert(Alert.AlertType.ERROR,
                    "Please select a train to delete",
                    ButtonType.OK);
            err.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this train?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            try {
                TrainManager.deleteTrain((Train) trainTable.getSelectionModel().getSelectedItem());
                trainTable.getItems().remove(trainTable.getSelectionModel().getSelectedItem());
                Alert info = new Alert(Alert.AlertType.INFORMATION, "train deleted successfully", ButtonType.OK);
                clearTextLabels();
                info.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearTextLabels() {
        arrivalStationL.setText("");
        arrivalTimeL.setText("");
        departureStationL.setText("");
        departureTimeL.setText("");
        maximumCapacityL.setText("");
        priceL.setText("");
        currentCapacityLabel.setText("");
    }

    public void viewAddTrainPage(ActionEvent actionEvent) throws IOException {
        navigateTo(actionEvent, "/com/trs/forms/ManageTrain.fxml");

    }

    @FXML
    public void viewEditTrainPage(ActionEvent actionEvent) {
        if (trainTable.getSelectionModel().getSelectedItem() != null) {
            ManageTrainController.selectedTrain = (Train) trainTable.getSelectionModel().getSelectedItem();
            ManageTrainController.editTrigger = true;
            try {
                navigateTo(actionEvent, "/com/trs/forms/ManageTrain.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new Alert(Alert.AlertType.ERROR, "Please select a train first").showAndWait();

    }

    public void disableButtons() {
        addTrainBtn.setDisable(true);
        editTrainBtn.setDisable(true);
        deleteTrainBtn.setDisable(true);
    }

    //method to turn the date and time into a timestamp
    private Timestamp getDateTime(String date, String hour, String minute) {
        return ManageTrainController.getTimestamp(date, hour, minute);
    }

    void populateTable() {
        try {
            ArrayList<Train> trains = (ArrayList<Train>) TrainManager.getAllTrains();

            for (Train train : trains) {
                trainTable.getItems().add(train);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void prepareColumns() {
        trainNumberColumn.setCellValueFactory(new PropertyValueFactory<>("trainNumber"));
        trainTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    }

    void prepareTable() {
        trainTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Train train = (Train) newSelection;
                departureStationL.setText(train.getDepartureStation());
                arrivalStationL.setText(train.getArrivalStation());
                departureTimeL.setText(train.getDepartureTime().toString());
                arrivalTimeL.setText(train.getArrivalTime().toString());
                maximumCapacityL.setText(String.valueOf(train.getMaximumCapacity()));
                priceL.setText(String.valueOf(train.getPrice()));
                currentCapacityLabel.setText(String.valueOf(train.getCurrentCapacity()));
            }
        });
    }


    private void isInitialized() {
        assert arrivalStationL != null : "fx:id=\"arrivalStationL\" was not injected: check your FXML file 'ViewTrain.fxml'.";
        assert arrivalTimeL != null : "fx:id=\"arrivalTimeL\" was not injected: check your FXML file 'ViewTrain.fxml'.";
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'ViewTrain.fxml'.";
        assert departureStationL != null : "fx:id=\"departureStationL\" was not injected: check your FXML file 'ViewTrain.fxml'.";
        assert departureTimeL != null : "fx:id=\"departureTimeL\" was not injected: check your FXML file 'ViewTrain.fxml'.";
        assert maximumCapacityL != null : "fx:id=\"maximumCapacityL\" was not injected: check your FXML file 'ViewTrain.fxml'.";
        assert priceL != null : "fx:id=\"priceL\" was not injected: check your FXML file 'ViewTrain.fxml'.";
        assert trainNumberColumn != null : "fx:id=\"trainNumberColumn\" was not injected: check your FXML file 'ViewTrain.fxml'.";
        assert trainTable != null : "fx:id=\"trainTable\" was not injected: check your FXML file 'ViewTrain.fxml'.";
        assert trainTypeColumn != null : "fx:id=\"trainTypeColumn\" was not injected: check your FXML file 'ViewTrain.fxml'.";
    }
    void setPrivileges() {
        if (!LoginController.IsAdmin()) {
            addTrainBtn.setDisable(true);
            editTrainBtn.setDisable(true);
            deleteTrainBtn.setDisable(true);
        }
    }
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isInitialized();
        populateTable();
        prepareColumns();
        prepareTable();
        setPrivileges();
    }


    public void handleTicketReserve(ActionEvent actionEvent) {
        try {
        if (trainTable.getSelectionModel().getSelectedItem() != null) {
            ReserveTicketController.selectedTrain = (Train) trainTable.getSelectionModel().getSelectedItem();
            ViewTicketsController.selectedTrain = (Train) trainTable.getSelectionModel().getSelectedItem();
            ReserveTicketController.reserveTrigger = true;
            ViewTicketsController.trainTrigger = true;

            navigateTo(actionEvent, "/com/trs/forms/ViewTickets.fxml");
            return;
            }
            }catch (IOException e) {
            e.printStackTrace();
        }
        new Alert(Alert.AlertType.ERROR, "Please Select A Train First", ButtonType.OK).showAndWait();
    }
}
