package pl.kk.quizmon.controllers.components;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import pl.kk.quizmon.models.Pokemon;

public class PokemonSelectorElementController {
    private Pokemon pokemon;
    @FXML
    private ImageView pokemonIcon;
    @FXML
    private Label pokemonId;

    public void setData(Pokemon pokemon) {
        Platform.runLater(() -> {
            this.pokemon = pokemon;
            pokemonIcon.setImage(pokemon.getIcon());
            pokemonId.setText(Integer.toString(pokemon.getId()));
        });
    }

    public Pokemon getData() {
        return pokemon;
    }
}
