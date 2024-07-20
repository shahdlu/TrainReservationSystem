package com.trs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class FormNavigator {
    static ViewTrainController viewTrainController = new ViewTrainController();
    static ManageOfficerController manageOfficerController = new ManageOfficerController();

    public static void viewLoginPage(ActionEvent actionEvent) throws IOException {
        Parent loginParent = FXMLLoader.load(Objects.requireNonNull(FormNavigator.class.getResource("/com/trs/forms/Login.fxml")));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }

    public static void viewManageOfficerPage(ActionEvent actionEvent) throws IOException {
        Parent officerPage = FXMLLoader.load(Objects.requireNonNull(FormNavigator.class.getResource("/com/trs/forms/ManageOfficer.fxml")));
        Scene officerPageScene = new Scene(officerPage);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(officerPageScene);
        window.show();
    }

    public static void viewTrainPage(ActionEvent actionEvent) throws IOException {
        Parent adminPage = FXMLLoader.load(Objects.requireNonNull(FormNavigator.class.getResource("/com/trs/forms/ViewTrain.fxml")));
        Scene adminPageScene = new Scene(adminPage);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(adminPageScene);
        window.show();
    }

    public static void viewManageTrainPage(ActionEvent actionEvent) throws IOException {
        Parent manageTrainPage = FXMLLoader.load(Objects.requireNonNull(FormNavigator.class.getResource("/com/trs/forms/ManageTrain.fxml")));
        Scene manageTrainPageScene = new Scene(manageTrainPage);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(manageTrainPageScene);
        window.show();
    }

    public static void viewReserveTicketPage(ActionEvent actionEvent) throws IOException {
        Parent reserveTicketPage = FXMLLoader.load(Objects.requireNonNull(FormNavigator.class.getResource("/com/trs/forms/ReserveTicket.fxml")));
        Scene reserveTicketPageScene = new Scene(reserveTicketPage);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(reserveTicketPageScene);
        window.show();
    }

    public static void viewOfficerPage(ActionEvent actionEvent) throws IOException {
        Parent reserveTicketPage = FXMLLoader.load(Objects.requireNonNull(FormNavigator.class.getResource("/com/trs/forms/ViewOfficer.fxml")));
        Scene reserveTicketPageScene = new Scene(reserveTicketPage);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(reserveTicketPageScene);
        window.show();
    }

    public static void navigateTo(ActionEvent actionEvent, String path) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(FormNavigator.class.getResource(path)));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    // navigate to the view train page but disable all buttons except the back button
    public static void viewTrainPageDisableButtons(ActionEvent actionEvent) throws IOException {
        viewTrainController.disableButtons();
        viewTrainPage(actionEvent);
    }

    public static void viewManageOfficerPageDisableButtons(ActionEvent actionEvent) throws IOException {
        manageOfficerController.disableIDTextField();
        viewManageOfficerPage(actionEvent);
    }

}
