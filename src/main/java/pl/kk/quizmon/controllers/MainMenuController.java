package pl.kk.quizmon.controllers;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import pl.kk.quizmon.models.Pokemon;
import pl.kk.quizmon.services.PokeApiService;
import pl.kk.quizmon.services.ViewManager;

import java.util.logging.Logger;

public class MainMenuController {
    @FXML
    protected void onPokedexButtonClick() {
        PokeApiService apiService = new PokeApiService();

        try {
            /*String jsonData = apiService.getData();
            Gson gson = new Gson();
            Pokemon pokemon = gson.fromJson(jsonData, Pokemon.class);
            System.out.println(pokemon.getName());
            System.out.println(pokemon.getId());*/

            ViewManager.getInstance().switchView(ViewManager.View.Pokedex);

        } catch (Exception e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

    @FXML
    protected void onAboutButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.About);
    }
}
