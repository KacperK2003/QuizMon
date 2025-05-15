package pl.kk.quizmon.controllers.components;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pl.kk.quizmon.events.SearchFinishedEvent;
import pl.kk.quizmon.infrastructure.EventBusProvider;
import pl.kk.quizmon.models.Pokemon;
import pl.kk.quizmon.services.PokeApiService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class SearchBarController {
    @FXML
    private TextField searchField;

    @FXML
    protected void onSearchClicked() {
        String searchString = searchField.getText();
        // Event o starcie szukania. Pokedex view nasłuchuje i włącza np. ładowanie.

        Task<Void> apiTask = new Task<>() {
            @Override
            protected Void call() {
                Pokemon pokemon = null;
                try {
                    pokemon = PokeApiService.getInstance().getPokemonData(searchString);
                } catch (IOException | URISyntaxException e) {
                    Logger.getGlobal().severe(e.getMessage());
                }

                if (pokemon == null)
                    pokemon = Pokemon.getUnknown();

                EventBusProvider.getEventBus().post(new SearchFinishedEvent(pokemon));

                return null;
            }
        };

        new Thread(apiTask).start();
    }
}
