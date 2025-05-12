package pl.kk.quizmon.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import pl.kk.quizmon.models.Pokemon;

public class PokedexListElementController {
    @FXML
    private ImageView pokemonIcon;
    @FXML
    private Label pokemonId;

    public void setData(Pokemon pokemon) {
        pokemonIcon.setImage(pokemon.getIcon());
        pokemonId.setText(Integer.toString(pokemon.getId()));
    }
}
