package pl.kk.quizmon.controllers;

import javafx.fxml.FXML;

import pl.kk.quizmon.services.ViewManager;

public class MainMenuController {
    @FXML
    protected void onPokedexButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.Pokedex);
    }

    @FXML
    protected void onAboutButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.About);
    }
}
