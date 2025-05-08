package pl.kk.quizmon.controllers;

import javafx.fxml.FXML;
import pl.kk.quizmon.utils.ViewManager;

public class MainMenuController {
    @FXML
    protected void onAboutButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.About);
    }
}
