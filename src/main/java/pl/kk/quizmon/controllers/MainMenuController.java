package pl.kk.quizmon.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MainMenuController {
    @FXML
    protected void onAboutButtonClick() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("TEST");
        alert.showAndWait();
    }
}
