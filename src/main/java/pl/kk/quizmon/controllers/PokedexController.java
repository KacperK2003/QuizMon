package pl.kk.quizmon.controllers;

import com.google.common.eventbus.Subscribe;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.kk.quizmon.QuizMonApplication;
import pl.kk.quizmon.controllers.components.PokemonSelectorController;
import pl.kk.quizmon.controllers.components.PokemonViewerController;
import pl.kk.quizmon.events.FetchPokemonDataEvent;
import pl.kk.quizmon.events.SearchFinishedEvent;
import pl.kk.quizmon.events.SelectorMovedEvent;
import pl.kk.quizmon.infrastructure.EventBusProvider;
import pl.kk.quizmon.models.Pokemon;
import pl.kk.quizmon.infrastructure.ViewManager;
import pl.kk.quizmon.services.DatabaseService;
import pl.kk.quizmon.services.PokeApiService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class PokedexController extends LifetimeController {
    private final String firstPokemonName = "Bulbasaur";

    @FXML
    private VBox root;
    @FXML
    private HBox mainHBox;

    private PokemonViewerController pokemonViewerController;
    private PokemonSelectorController pokemonSelectorController;

    @FXML
    public void initialize() {
        FXMLLoader loaderPokemonViewer = new FXMLLoader(QuizMonApplication.class.getResource("views/components/pokemon-viewer.fxml"));
        FXMLLoader loaderPokemonSelector = new FXMLLoader(QuizMonApplication.class.getResource("views/components/pokemon-selector.fxml"));
        FXMLLoader loaderSearchBar = new FXMLLoader(QuizMonApplication.class.getResource("views/components/searchbar.fxml"));

        try {
            Node viewer = loaderPokemonViewer.load();
            pokemonViewerController = loaderPokemonViewer.getController();
            mainHBox.getChildren().add(viewer);
            Node selector = loaderPokemonSelector.load();
            pokemonSelectorController = loaderPokemonSelector.getController();
            mainHBox.getChildren().add(selector);
            Node searchBar = loaderSearchBar.load();
            root.getChildren().addFirst(searchBar);
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        EventBusProvider.getEventBus().register(pokemonSelectorController);

        Task<Void> apiTask = new Task<>() {
            @Override
            protected Void call() {
                Pokemon pokemon = null;
                try {
                    pokemon = PokeApiService.getInstance().getPokemonData(firstPokemonName);
                } catch (IOException | URISyntaxException e) {
                    Logger.getGlobal().severe(e.getMessage());
                }

                if (pokemon == null)
                    pokemon = Pokemon.getUnknown();

                EventBusProvider.getEventBus().post(new FetchPokemonDataEvent(pokemon));

                return null;
            }
        };

        new Thread(apiTask).start();
    }

    @Override
    public void onUnload() {
        super.onUnload();
        EventBusProvider.getEventBus().unregister(pokemonSelectorController);
    }

    @FXML
    protected void onBackButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.MainMenu);
    }

    @FXML
    protected void onFavouriteClick() {
        Pokemon pokemon = pokemonViewerController.getCurrentPokemon();

        Task<Void> databaseTask = new Task<>() {
            @Override
            protected Void call() {
                DatabaseService.getInstance().addPokemon(pokemon);
                return null;
            }
        };
        new Thread(databaseTask).start();
    }

    @Subscribe
    public void onSearchFinished(SearchFinishedEvent event) {
        Pokemon result = event.getResult();
        if (result == null || result == Pokemon.getUnknown()) {
            pokemonViewerController.setData(Pokemon.getUnknown());
            return;
        }

        pokemonViewerController.setData(result);
        pokemonSelectorController.setData(result);
    }

    @Subscribe
    public void onFetchPokemonData(FetchPokemonDataEvent event) {
        Pokemon result = event.getResult();
        if (result == null || result == Pokemon.getUnknown()) {
            pokemonViewerController.setData(Pokemon.getUnknown());
            return;
        }

        pokemonViewerController.setData(result);
        pokemonSelectorController.setData(result);
    }

    @Subscribe
    public void onSelectorMoved(SelectorMovedEvent event) {
        pokemonViewerController.setData(event.getNewMiddlePokemon());
    }
}
