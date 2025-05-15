package pl.kk.quizmon.controllers.components;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import pl.kk.quizmon.events.SearchFinishedEvent;
import pl.kk.quizmon.infrastructure.EventBusProvider;
import pl.kk.quizmon.models.Pokemon;

public class PokemonViewerController {
    @FXML
    private ImageView pokemonSprite;
    @FXML
    private Label pokemonId;
    @FXML
    private Label pokemonName;

    @FXML
    public void initialize() {
        EventBusProvider.getEventBus().register(this);
    }

    public void setData(Pokemon pokemon) {
        Platform.runLater(() -> {
            pokemonSprite.setImage(pokemon.getSprite());
            pokemonId.setText("ID: " + pokemon.getId());
            pokemonName.setText(pokemon.getName().toUpperCase());
        });
    }

    @Subscribe
    public void onSearchFinished(SearchFinishedEvent event) {
        Pokemon result = event.getResult();
        if (result == null) {
            setData(new Pokemon(0, "???"));
            return;
        }

        setData(event.getResult());
    }
}
