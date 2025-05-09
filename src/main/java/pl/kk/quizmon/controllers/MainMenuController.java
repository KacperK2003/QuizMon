package pl.kk.quizmon.controllers;

import javafx.fxml.FXML;
import pl.kk.quizmon.services.PokeApiService;
import pl.kk.quizmon.services.ViewManager;

import java.util.logging.Logger;

public class MainMenuController {
    @FXML
    protected void onPokedexButtonClick() {
        PokeApiService apiService = new PokeApiService();

        try {
            System.out.println(apiService.getData());
        } catch (Exception e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

    @FXML
    protected void onAboutButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.About);
    }
}
