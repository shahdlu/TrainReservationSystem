package com.trs.controllers;

import com.trs.api.managers.TicketManager;
import com.trs.api.managers.TrainManager;
import com.trs.modules.SystemAdmin;
import com.trs.modules.Train;
import com.trs.modules.tickets.FirstClassTicket;
import com.trs.modules.tickets.SecondClasssTicket;
import com.trs.modules.tickets.ThirdClassTicket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.ResourceBundle;
import static com.trs.controllers.ManageTrainController.showErrorMessage;
import static com.trs.controllers.ManageTrainController.tryParse;

public class ReserveTicketController extends FormNavigator implements Initializable {

    @FXML
    public Label totalPriceLabel;
    @FXML
    public TextField singlePriceTextField;
    @FXML
    public ComboBox degreeComboBox;
    @FXML
    public DatePicker reservationDatePicker;
    public ComboBox trainPickerComboBox;
    static Train selectedTrain;
    static boolean reserveTrigger;
    private Train trainFromComboBox;
    int number;
    int trainNumber;
    String degree;
    Date date;
    String price;
    ArrayList<Integer> trainIDs = new ArrayList<>();

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label label;
    @FXML
    private TextField trainNumberTextField;
    @FXML
    private TextField totalPriceTextField;
    @FXML
    private Button bookButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField ticketsNumberTextField;
    @FXML
    private Button backButton;

    public ReserveTicketController() {
        super();
    }
    public boolean hasDate(){
        return reservationDatePicker.getValue() != null;
    }

    public boolean isNumber() {
        if ( !(tryParse(ticketsNumberTextField.getText()))) {
            return false;
        }return true;
    }
    @FXML
    void backHandle(ActionEvent event) throws IOException {
        if(LoginController.IsAdmin()) {
            navigateTo(event, "/com/trs/forms/AdminActionPage.fxml");
            return;
        }
        navigateTo(event,"/com/trs/forms/OfficerActionPage.fxml");
    }

    @FXML
    void bookHandle(ActionEvent event) throws SQLException {
        if(isNumber()&&hasDate()){
        System.out.println(reservationDatePicker.getValue().toString());
        int numberOfTickets = Integer.parseInt(ticketsNumberTextField.getText());
        int price = Integer.parseInt(singlePriceTextField.getText());
        getValuesFromFields();
        Train train = new SystemAdmin().findTrain(trainNumber);
        if( train.isFull()|| train.getCurrentCapacity() < numberOfTickets) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Train's capacity isn't enough for this reservation");
            alert.setContentText("Please choose another train");
            alert.showAndWait();
            return;
        }
        for(int i = 0; i < numberOfTickets; i++) {
            switch (degree) {
                case "First Class" -> new SystemAdmin().bookTicket(
                        new FirstClassTicket(TicketManager.generateTicketID(trainNumber),
                        price / numberOfTickets, trainNumber, date),
                        new SystemAdmin().findTrain(trainNumber));
                case "Second Class" -> new SystemAdmin().bookTicket(
                        new SecondClasssTicket(TicketManager.generateTicketID(trainNumber),
                        price / numberOfTickets, trainNumber, date),
                        new SystemAdmin().findTrain(trainNumber));
                case "Third Class" -> new SystemAdmin().bookTicket(
                        new ThirdClassTicket(TicketManager.generateTicketID(trainNumber),
                        price / numberOfTickets, trainNumber, date),
                        new SystemAdmin().findTrain(trainNumber));
            }
        }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Done");
            alert.setHeaderText("Successfully");
            alert.setContentText("Added Successfully");
            alert.showAndWait();
            clearFields();
    } else {
            showErrorMessage("Failed on Booking Ticket");


        }
    }
    public void clearFields(){
        ticketsNumberTextField.clear();
        singlePriceTextField.clear();

    }

    void getAllTrainIDs() throws SQLException {
        ArrayList<Train> trains = (ArrayList<Train>) TrainManager.getAllTrains();
        for(Train t : trains){
            trainIDs.add(t.getTrainNumber());
        }
    }
    void initializeComboBoxes () throws SQLException {
        degreeComboBox.getItems().addAll("First Class", "Second Class", "Third Class");
        degreeComboBox.getSelectionModel().select(0);
        if(reserveTrigger){
            trainPickerComboBox.getItems().add(selectedTrain.getTrainNumber());
            trainPickerComboBox.setValue(selectedTrain.getTrainNumber());
            return;
        }
        trainPickerComboBox.getItems().addAll(trainIDs);
        trainPickerComboBox.getSelectionModel().select(0);
    }

    void setTicketClassPrice() throws SQLException {
        String degree = degreeComboBox.getValue().toString();
        int trainNumber = Integer.parseInt(trainPickerComboBox.getValue().toString());
        Train train = new SystemAdmin().findTrain(trainNumber);
        switch (degree) {
            case "First Class" -> singlePriceTextField.setText(String.valueOf(train.getPrice() * 3));
            case "Second Class" -> singlePriceTextField.setText(String.valueOf(train.getPrice() * 2));
            case "Third Class" -> singlePriceTextField.setText(String.valueOf(train.getPrice()));
        }
    }

    //change the price of the ticket according to the train
    @FXML
    void trainPickerComboBoxHandle(ActionEvent event) throws SQLException {
            setTicketClassPrice();
            updateTotalPrice();
    }
    @FXML
    void classPickerComboBoxHandle(ActionEvent event) throws SQLException {

            setTicketClassPrice();
            updateTotalPrice();

    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            isInitialized();
            getAllTrainIDs();
            initializeComboBoxes();
            getValuesFromFields();
            setTicketClassPrice();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void isInitialized(){
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'ReserveTicket.fxml'.";
        assert trainNumberTextField != null : "fx:id=\"trainNumberTextField\" was not injected: check your FXML file 'ReserveTicket.fxml'.";
        assert totalPriceTextField != null : "fx:id=\"totalPriceTextField\" was not injected: check your FXML file 'ReserveTicket.fxml'.";
        assert bookButton != null : "fx:id=\"bookButton\" was not injected: check your FXML file 'ReserveTicket.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'ReserveTicket.fxml'.";
        assert ticketsNumberTextField != null : "fx:id=\"ticketsNumberTextField\" was not injected: check your FXML file 'ReserveTicket.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'ReserveTicket.fxml'.";

    }
    //a method to get all values from the fields
    void getValuesFromFields() {
        try {
            number = trainPickerComboBox.getSelectionModel().getSelectedIndex();
            trainNumber = Integer.parseInt(trainPickerComboBox.getValue().toString());
            degree = degreeComboBox.getValue().toString();
            date = Date.valueOf(reservationDatePicker.getValue().toString());
            price = singlePriceTextField.getText();
        }
        catch (NullPointerException ignored){}

    }

    public void updateTotalPrice(){
        System.out.println("HERE =====> update total price");
        if(!tryParse(ticketsNumberTextField.getText()) && !(ticketsNumberTextField.getText().isEmpty() || ticketsNumberTextField.getText().isBlank())  ){
            new Alert(Alert.AlertType.ERROR, "Please enter a valid number of tickets").showAndWait();
            ticketsNumberTextField.clear();
            totalPriceLabel.setText("0");
            return;
        }

        if(!ticketsNumberTextField.getText().isEmpty() || !ticketsNumberTextField.getText().isBlank()){
            int numberOfTickets = Integer.parseInt(ticketsNumberTextField.getText());
            int singlePrice = Integer.parseInt(singlePriceTextField.getText());
            totalPriceLabel.setText(String.valueOf(numberOfTickets * singlePrice));
        }
    }
    @FXML
    public void HandleTotalPrice(KeyEvent keyEvent) {
            updateTotalPrice();

    }

}
