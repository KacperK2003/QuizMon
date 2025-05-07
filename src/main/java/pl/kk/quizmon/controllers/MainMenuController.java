package pl.kk.quizmon.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import pl.kk.quizmon.utils.ViewManager;

public class MainMenuController {
    @FXML
    protected void onAboutButtonClick(ActionEvent event) {
        ViewManager.getInstance().switchView(ViewManager.View.About);
    }
}
