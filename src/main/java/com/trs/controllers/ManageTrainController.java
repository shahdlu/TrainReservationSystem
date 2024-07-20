package com.trs.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.trs.api.managers.TrainManager;
import com.trs.modules.SystemAdmin;
import com.trs.modules.Train;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class ManageTrainController extends FormNavigator implements Initializable {

    public static boolean editTrigger;
    public static Train selectedTrain;
    public static boolean adminTrigger;
    int trainNumber;
    String departureStation;
    String status;
    String arrivalStation;
    String type;
    int maxCapacity;
    int standardPrice;
    private TrainManager trainManager = new TrainManager();
    private String departureDate;
    private String departureHour;
    private String departureMinute;
    private String arrivalDate;
    private String arrivalHour;
    private String arrivalMinute;
    private Timestamp departureDateTime;
    private Timestamp arrivalDateTime;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField maxCapacityTextField;
    @FXML
    private TextField trainNumberTextField;
    @FXML
    private TextField departureHourTextField;
    @FXML
    private TextField departureStationTextField;
    @FXML
    private TextField arrivalStationTextField;
    @FXML
    private TextField arrivalHourTextField;
    @FXML
    private Button addtrainButton;
    @FXML
    private Button exitButton;
    @FXML
    private TextField standardPriceTextField;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField departureMinuteTextField;
    @FXML
    private TextField arrivalMinuteTextField;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private DatePicker arrivalDatePicker;

    public ManageTrainController() throws SQLException {
        super();

    }

    static Timestamp getTimestamp(String date, String hour, String minute) {
        String[] dateArray = date.split("-");
        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);
        int hourInt = Integer.parseInt(hour);
        int minuteInt = Integer.parseInt(minute);
        return Timestamp.valueOf(year + "-" + month + "-" + day + " " + hourInt + ":" + minuteInt + ":00");
    }

    public String[] timestampToString(Timestamp timestamp) {
        String[] finalDate = new String[3];
        String[] date = timestamp.toString().split(" ");
        finalDate[0] = date[0];
        finalDate[1] = date[1].split(":")[0];
        finalDate[2] = date[1].split(":")[1];
        return finalDate;
    }

    public void setFields() {
        trainNumberTextField.setText(String.valueOf(selectedTrain.getTrainNumber()));
        departureStationTextField.setText(selectedTrain.getDepartureStation());
        arrivalStationTextField.setText(selectedTrain.getArrivalStation());
        departureDatePicker.setValue(LocalDate.parse(timestampToString(selectedTrain.getDepartureTime())[0]));
        departureHourTextField.setText(timestampToString(selectedTrain.getDepartureTime())[1]);
        departureMinuteTextField.setText(timestampToString(selectedTrain.getDepartureTime())[2]);
        arrivalDatePicker.setValue(LocalDate.parse(timestampToString(selectedTrain.getArrivalTime())[0]));
        arrivalHourTextField.setText(timestampToString(selectedTrain.getArrivalTime())[1]);
        arrivalMinuteTextField.setText(timestampToString(selectedTrain.getArrivalTime())[2]);
        maxCapacityTextField.setText(String.valueOf(selectedTrain.getMaximumCapacity()));
        standardPriceTextField.setText(String.valueOf(selectedTrain.getPrice()));
        typeTextField.setText(selectedTrain.getType());
    }

    public static void showErrorMessage(String e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Failed");
        alert.setContentText(e);
        alert.showAndWait();
    }
    private boolean checkDateTime() {
        return checkDate() && checkMinutes() && checkHours();
    }
    private boolean numberExists(){
        return TrainManager.trainNumberExists(Integer.parseInt(trainNumberTextField.getText()));
    }


    @FXML
    public void addTrain(ActionEvent actionEvent) throws SQLException {
        if (!isText() || !isNumber()) {
            showErrorMessage("Invalid data");
            return;
        }
        if(!checkDateTime()){
            showErrorMessage("Invalid date or time");
            return;
        }
        if (!editTrigger) {
            if (!numberExists()){
                add();
                return;
            }
            showErrorMessage("Id Exists");
            return;
        }
        update();
    }
    public void clearFields(){
        maxCapacityTextField.clear();
        arrivalMinuteTextField.clear();
        arrivalHourTextField.clear();
        arrivalStationTextField.clear();
        standardPriceTextField.clear();
        trainNumberTextField.clear();
        departureMinuteTextField.clear();
        departureHourTextField.clear();
        departureStationTextField.clear();
        typeTextField.clear();
    }


    private void update() throws SQLException {
        if (checkValues()) {
            Train train = getTrainValues();
            String status = checkSuccess(train);
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

    public boolean checkValues() {
        return !trainNumberTextField.getText().isEmpty() &&
                !departureStationTextField.getText().isEmpty() &&
                !arrivalStationTextField.getText().isEmpty() &&
                !departureDatePicker.getValue().toString().isEmpty() &&
                !departureHourTextField.getText().isEmpty() &&
                !departureMinuteTextField.getText().isEmpty() &&
                !arrivalDatePicker.getValue().toString().isEmpty() &&
                !arrivalHourTextField.getText().isEmpty() &&
                !arrivalMinuteTextField.getText().isEmpty() &&
                !maxCapacityTextField.getText().isEmpty() &&
                !standardPriceTextField.getText().isEmpty() &&
                !typeTextField.getText().isEmpty();
    }

    private String checkSuccess(Train train) throws SQLException {
        if (editTrigger) {
            status = new SystemAdmin().updateTrain(train);
            return status;
        }
        status = new SystemAdmin().addTrain(train);
        return status;
    }

    private void add() throws SQLException {
        if (checkValues()) {
            String status = checkSuccess(getTrainValues());
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
    public boolean checkDate(){
        if(departureDatePicker.getValue() == null || arrivalDatePicker.getValue() == null){
            return false;
        }
        if(departureDatePicker.getValue().isAfter( arrivalDatePicker.getValue())){
            return false;
        }
            return true;
    }
    public boolean checkHours(){
        if(Integer.parseInt(departureHourTextField.getText())>24 || Integer.parseInt(arrivalHourTextField.getText())>24||Integer.parseInt(departureHourTextField.getText())<0 || Integer.parseInt(arrivalHourTextField.getText())<0){
            return false;
        }
        else {
            return true;
        }


    }
    public boolean isNumber() {
        return tryParse(standardPriceTextField.getText()) &&
                tryParse(maxCapacityTextField.getText()) &&
                tryParse(arrivalHourTextField.getText()) &&
                tryParse(arrivalMinuteTextField.getText()) &&
                tryParse(departureHourTextField.getText()) &&
                tryParse(trainNumberTextField.getText()) &&
                tryParse(departureMinuteTextField.getText());
    }

    public boolean isText() {
        return !tryParse(typeTextField.getText()) && !tryParse(departureStationTextField.getText()) && !tryParse(arrivalStationTextField.getText());
    }
    public static boolean tryParse(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean checkMinutes(){
        if(Integer.parseInt(departureMinuteTextField.getText())<0 || Integer.parseInt(arrivalMinuteTextField.getText())<0||Integer.parseInt(departureMinuteTextField.getText())>60 || Integer.parseInt(arrivalMinuteTextField.getText())>60){
            return false;
        }
        else {
            return true;
        }


    }

    private void editTrain() throws SQLException {
        trainManager.updateTrain(getTrainValues());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Done");
        alert.setHeaderText("Edited Successfully");
        alert.setContentText("data edited successfully");
        alert.showAndWait();
    }

    private boolean checkFields() {
        return !trainNumberTextField.getText().isEmpty() &&
                !departureStationTextField.getText().isEmpty() &&
                !arrivalStationTextField.getText().isEmpty() &&
                !departureDatePicker.getValue().toString().isEmpty() &&
                !departureHourTextField.getText().isEmpty() &&
                !departureMinuteTextField.getText().isEmpty() &&
                !arrivalDatePicker.getValue().toString().isEmpty() &&
                !arrivalHourTextField.getText().isEmpty() &&
                !arrivalMinuteTextField.getText().isEmpty() &&
                !maxCapacityTextField.getText().isEmpty() &&
                !standardPriceTextField.getText().isEmpty() &&
                !typeTextField.getText().isEmpty();
    }

    @FXML
    public void exitHandle(ActionEvent actionEvent) throws IOException {
        if (LoginController.IsAdmin())
            ViewTrainController.adminTrigger = true;
        navigateTo(actionEvent, "/com/trs/forms/ViewTrain.fxml");

    }

    private Timestamp getDateTime(String date, String hour, String minute) {
        return getTimestamp(date, hour, minute);
    }

    public Train getTrainValues() throws SQLException {
        trainNumber = Integer.parseInt(trainNumberTextField.getText());
        departureStation = departureStationTextField.getText();
        arrivalStation = arrivalStationTextField.getText();
        type = typeTextField.getText();
        maxCapacity = Integer.parseInt(maxCapacityTextField.getText());
        standardPrice = Integer.parseInt(standardPriceTextField.getText());
        departureDate = departureDatePicker.getValue().toString();
        departureHour = departureHourTextField.getText();
        departureMinute = departureMinuteTextField.getText();
        arrivalDate = arrivalDatePicker.getValue().toString();
        arrivalHour = arrivalHourTextField.getText();
        arrivalMinute = arrivalMinuteTextField.getText();
        departureDateTime = getDateTime(departureDate, departureHour, departureMinute);
        arrivalDateTime = getDateTime(arrivalDate, arrivalHour, arrivalMinute);
        System.out.println(departureDateTime);
        System.out.println(arrivalDateTime);
        return new Train(trainNumber, type, departureDateTime, arrivalDateTime, departureStation, arrivalStation, maxCapacity, standardPrice);
    }


    public void isInitialized() {
        assert maxCapacityTextField != null : "fx:id=\"maxCapacityTextField\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert trainNumberTextField != null : "fx:id=\"trainNumberTextField\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert departureHour != null : "fx:id=\"departureHour\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert departureStationTextField != null : "fx:id=\"departureStationTextField\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert arrivalStationTextField != null : "fx:id=\"arrivalStationTextField\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert arrivalHour != null : "fx:id=\"arrivalHour\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert addtrainButton != null : "fx:id=\"addtrainButton\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert standardPriceTextField != null : "fx:id=\"standardPriceTextField\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert typeTextField != null : "fx:id=\"typeTextField\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert departureMinute != null : "fx:id=\"departureMinute\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert arrivalMinute != null : "fx:id=\"arrivalMinute\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert departureDatePicker != null : "fx:id=\"departureDatePicker\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
        assert arrivalDatePicker != null : "fx:id=\"arrivalDatePicker\" was not injected: check your FXML file 'ManageTrainController.fxml'.";
    }


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isInitialized();
        if (editTrigger) {
            System.out.println(selectedTrain.getPrice());
            setFields();
        }

    }
}
