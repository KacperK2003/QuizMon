package pl.kk.quizmon.controllers.components;

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

    public void setData(Pokemon pokemon) {
        pokemonSprite.setImage(pokemon.getSprite());
        pokemonId.setText("ID: " + pokemon.getId());
        pokemonName.setText(pokemon.getName().toUpperCase());
    }
}
