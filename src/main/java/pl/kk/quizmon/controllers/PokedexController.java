package pl.kk.quizmon.controllers;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.kk.quizmon.QuizMonApplication;
import pl.kk.quizmon.controllers.components.PokedexListElementController;
import pl.kk.quizmon.controllers.components.PokemonViewerController;
import pl.kk.quizmon.controllers.components.SearchBarController;
import pl.kk.quizmon.events.SearchFinishedEvent;
import pl.kk.quizmon.infrastructure.EventBusProvider;
import pl.kk.quizmon.models.Pokemon;
import pl.kk.quizmon.services.PokeApiService;
import pl.kk.quizmon.infrastructure.ViewManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class PokedexController {
    @FXML
    private VBox root;
    @FXML
    private HBox mainHBox;

    @FXML
    public void initialize() {
        FXMLLoader loaderPokemonViewer = new FXMLLoader(QuizMonApplication.class.getResource("views/components/pokemon-viewer.fxml"));
        FXMLLoader loaderPokedexListElement = new FXMLLoader(QuizMonApplication.class.getResource("views/components/pokedex-list-element.fxml"));
        FXMLLoader loaderSearchBar = new FXMLLoader(QuizMonApplication.class.getResource("views/components/searchbar.fxml"));

        try {
            Node viewer = loaderPokemonViewer.load();
            //PokemonViewerController pokemonViewerController = loaderPokemonViewer.getController();
            mainHBox.getChildren().add(viewer);
            Node element = loaderPokedexListElement.load();
            //PokedexListElementController pokedexListElementController = loaderPokedexListElement.getController();
            mainHBox.getChildren().add(element);
            Node searchBar = loaderSearchBar.load();
            //SearchBarController searchBarController = loaderSearchBar.getController();
            root.getChildren().addFirst(searchBar);
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

    @FXML
    protected void onBackButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.MainMenu);
    }
}
