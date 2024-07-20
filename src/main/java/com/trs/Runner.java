package com.trs;

import com.trs.api.managers.TicketManager;
import com.trs.controllers.FormNavigator;
import com.trs.modules.Train;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Runner extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent loginParent = FXMLLoader.load(Objects.requireNonNull(FormNavigator.class.getResource("/com/trs/forms/Login.fxml")));
        Scene loginScene = new Scene(loginParent);
        stage.setScene(loginScene);
        stage.show();
    }
}
