package com.trs.controllers;

import com.trs.api.managers.OfficerManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class LoginController extends FormNavigator implements Initializable {
    public static int type;
    private static short tryCounter = 0;
    @FXML
    public Button loginBtn;
    @FXML
    public Label youFailedLabel;
    @FXML
    public Label timeLabel;
    @FXML
    public Label secondsLabel;
    OfficerManager officerManager = new OfficerManager();
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private RadioButton officerRadio;
    @FXML
    private RadioButton adminRadio;
    @FXML
    private ToggleGroup typePicker;
    public LoginController() {
        super();
    }

    public static void setIsAdmin(boolean b) {
        isAdmin = b;
    }

    private boolean isTextEmpty() {
        return usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty();
    }

    private void timeout() {
        new Thread(() -> {
            Platform.runLater(() -> {
                youFailedLabel.setVisible(true);
                loginBtn.setDisable(true);
                usernameTextField.setDisable(true);
                passwordTextField.setDisable(true);
            });
            try {
                Thread.sleep(5000);
            }
            catch(InterruptedException ignored) {
            }
            Platform.runLater(() ->{
                youFailedLabel.setVisible(false);
                loginBtn.setDisable(false);
                usernameTextField.setDisable(false);
                passwordTextField.setDisable(false);});}).start();
    }

    private static boolean isAdmin = false;
    public static boolean IsAdmin() {
        return isAdmin;
    }
    @FXML
    public void processLogin(ActionEvent actionEvent) throws SQLException, InterruptedException, IOException {
        if (tryCounter == 2) {
            new Alert(Alert.AlertType.ERROR, "You have exceeded the maximum number of login attempts. Please try again later.").show();
            timeout();
            tryCounter = 0;
            return;
        }
        if (isTextEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter username and password").showAndWait();
            return;
        }
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        type = adminRadio.isSelected() ? 0 : 1;

        if (officerManager.getTicketingOfficer(username, password, type) == null) {
            System.out.println(officerManager.getTicketingOfficer(username, password, type));
            tryCounter++;
            new Alert(Alert.AlertType.ERROR, "username or password incorrect").showAndWait();
            return;
        }
        if (type == 0) {
            navigateTo(actionEvent, "/com/trs/forms/AdminActionPage.fxml");
            isAdmin = true;
        } else {
            navigateTo(actionEvent, "/com/trs/forms/OfficerActionPage.fxml");
            isAdmin = false;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isInitialized();
        youFailedLabel.setVisible(false);

    }

    public void isInitialized() {
        assert adminRadio != null : "fx:id=\"adminRadio\" was not injected: check your FXML file 'Login.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'Login.fxml'.";
        assert officerRadio != null : "fx:id=\"officerRadio\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'Login.fxml'.";
        assert secondsLabel != null : "fx:id=\"secondsLabel\" was not injected: check your FXML file 'Login.fxml'.";
        assert timeLabel != null : "fx:id=\"timeLabel\" was not injected: check your FXML file 'Login.fxml'.";
        assert typePicker != null : "fx:id=\"typePicker\" was not injected: check your FXML file 'Login.fxml'.";
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'Login.fxml'.";
        assert youFailedLabel != null : "fx:id=\"youFailedLabel\" was not injected: check your FXML file 'Login.fxml'.";
    }
}
