package pl.kk.quizmon.controllers.components;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import pl.kk.quizmon.QuizMonApplication;
import pl.kk.quizmon.events.PokemonRemovedEvent;
import pl.kk.quizmon.infrastructure.EventBusProvider;
import pl.kk.quizmon.models.Pokemon;
import pl.kk.quizmon.services.DatabaseService;

import java.io.IOException;
import java.util.logging.Logger;

public class FavouritePokemonViewerController {
    @FXML
    private HBox mainHBox;

    private PokemonViewerController pokemonViewerController;

    @FXML
    public void initialize() {
        FXMLLoader pokemonViewerLoader = new FXMLLoader(QuizMonApplication.class.getResource("views/components/pokemon-viewer.fxml"));

        try {
            Node viewer = pokemonViewerLoader.load();
            pokemonViewerController = pokemonViewerLoader.getController();
            mainHBox.getChildren().add(viewer);
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

    public void setData(Pokemon pokemon) {
        pokemonViewerController.setData(pokemon);
    }

    @FXML
    protected void onRemoveFromFavouriteClick() {
        FavouritePokemonViewerController sender = this;
        Task<Void> databaseTask = new Task<Void>() {
            @Override
            protected Void call() {
                DatabaseService.getInstance().removeFromDatabase(pokemonViewerController.getCurrentPokemon());

                EventBusProvider.getEventBus().post(new PokemonRemovedEvent(sender));
                return null;
            }
        };

        new Thread(databaseTask).start();
    }
}
