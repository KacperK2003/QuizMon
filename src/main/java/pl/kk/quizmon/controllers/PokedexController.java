package pl.kk.quizmon.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import pl.kk.quizmon.models.Pokemon;
import pl.kk.quizmon.services.PokeApiService;
import pl.kk.quizmon.services.ViewManager;

import java.util.logging.Logger;

public class PokedexController {
    @FXML
    private TextArea pokemonName;

    @FXML
    private ImageView pokemonSprite;

    @FXML
    public void initialize() {
        PokeApiService pokeApiService = new PokeApiService();
        try {
            Pokemon pokemon = pokeApiService.getPokemonData();
            pokemonName.setText(pokemon.getName());
            pokemonSprite.setImage(pokemon.getSprite());
            pokemonSprite.autosize();
        } catch (Exception e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

    @FXML
    protected void onBackButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.MainMenu);
    }
}
