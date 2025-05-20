package pl.kk.quizmon.controllers.components;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import pl.kk.quizmon.models.Pokemon;

public class PokemonViewerController {
    @FXML
    private ImageView pokemonSprite;
    @FXML
    private Label pokemonId;
    @FXML
    private Label pokemonName;

    private Pokemon currentPokemon;

    public void setData(Pokemon pokemon) {
        currentPokemon = pokemon;
        Platform.runLater(() -> {
            pokemonSprite.setImage(pokemon.getSprite());
            pokemonId.setText("ID: " + pokemon.getId());
            pokemonName.setText(pokemon.getName().toUpperCase());
        });
    }

    public Pokemon getCurrentPokemon() {
        return currentPokemon;
    }
}
