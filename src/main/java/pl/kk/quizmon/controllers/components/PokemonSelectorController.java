package pl.kk.quizmon.controllers.components;

import com.google.common.eventbus.Subscribe;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import pl.kk.quizmon.QuizMonApplication;
import pl.kk.quizmon.events.FetchUpAndDownPokemonDataEvent;
import pl.kk.quizmon.events.SelectorMovedEvent;
import pl.kk.quizmon.infrastructure.EventBusProvider;
import pl.kk.quizmon.models.Pokemon;
import pl.kk.quizmon.services.PokeApiService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class PokemonSelectorController {
    @FXML
    private VBox mainVBox;

    private PokemonSelectorElementController controllerUp;
    private PokemonSelectorElementController controllerMiddle;
    private PokemonSelectorElementController controllerDown;

    @FXML
    public void initialize() {
        FXMLLoader loaderPokedexListElementUp = new FXMLLoader(QuizMonApplication.class.getResource("views/components/pokemon-selector-element.fxml"));
        FXMLLoader loaderPokedexListElementMiddle = new FXMLLoader(QuizMonApplication.class.getResource("views/components/pokemon-selector-element.fxml"));
        FXMLLoader loaderPokedexListElementDown = new FXMLLoader(QuizMonApplication.class.getResource("views/components/pokemon-selector-element.fxml"));

        try {
            Node elementUp = loaderPokedexListElementUp.load();
            controllerUp = loaderPokedexListElementUp.getController();
            Node elementMiddle = loaderPokedexListElementMiddle.load();
            controllerMiddle = loaderPokedexListElementMiddle.getController();
            Node elementDown = loaderPokedexListElementDown.load();
            controllerDown = loaderPokedexListElementDown.getController();
            mainVBox.getChildren().addAll(elementUp, elementMiddle, elementDown);
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

    @FXML
    protected void onDownButton() {
        Pokemon pokemonDown = controllerDown.getData();
        Pokemon pokemonMiddle = controllerMiddle.getData();

        controllerUp.setData(pokemonMiddle);
        controllerMiddle.setData(pokemonDown);

        int newUpId = incrementId(pokemonDown.getId());

        Task<Void> apiTask = new Task<>() {
            @Override
            protected Void call() {
                Pokemon newPokemonDown = null;
                try {
                    newPokemonDown = PokeApiService.getInstance().getPokemonData(Integer.toString(newUpId));
                } catch (IOException | URISyntaxException e) {
                    Logger.getGlobal().severe(e.getMessage());
                }

                if (newPokemonDown == null)
                    newPokemonDown = Pokemon.getUnknown();

                EventBusProvider.getEventBus().post(new SelectorMovedEvent(newPokemonDown, pokemonDown, false));

                return null;
            }
        };

        new Thread(apiTask).start();
    }

    @FXML
    protected void onUpButton() {
        Pokemon pokemonUp = controllerUp.getData();
        Pokemon pokemonMiddle = controllerMiddle.getData();

        controllerDown.setData(pokemonMiddle);
        controllerMiddle.setData(pokemonUp);

        int newUpId = decrementId(pokemonUp.getId());

        Task<Void> apiTask = new Task<>() {
            @Override
            protected Void call() {
                Pokemon newPokemonUp = null;
                try {
                    newPokemonUp = PokeApiService.getInstance().getPokemonData(Integer.toString(newUpId));
                } catch (IOException | URISyntaxException e) {
                    Logger.getGlobal().severe(e.getMessage());
                }

                if (newPokemonUp == null)
                    newPokemonUp = Pokemon.getUnknown();

                EventBusProvider.getEventBus().post(new SelectorMovedEvent(newPokemonUp, pokemonUp, true));

                return null;
            }
        };

        new Thread(apiTask).start();
    }

    public void setData(Pokemon pokemon) {
        if (pokemon.getName().equals("???")) {
            controllerUp.setData(pokemon);
            controllerMiddle.setData(pokemon);
            controllerDown.setData(pokemon);
            return;
        }

        controllerMiddle.setData(pokemon);
        int upId = decrementId(pokemon.getId()), downId = incrementId(pokemon.getId());

        Task<Void> apiTask = new Task<>() {
            @Override
            protected Void call() {
                Pokemon pokemonUp = null, pokemonDown = null;
                try {
                    pokemonUp = PokeApiService.getInstance().getPokemonData(Integer.toString(upId));
                    pokemonDown = PokeApiService.getInstance().getPokemonData(Integer.toString(downId));
                } catch (IOException | URISyntaxException e) {
                    Logger.getGlobal().severe(e.getMessage());
                }

                if (pokemonUp == null)
                    pokemonUp = Pokemon.getUnknown();

                if (pokemonDown == null)
                    pokemonDown = Pokemon.getUnknown();

                EventBusProvider.getEventBus().post(new FetchUpAndDownPokemonDataEvent(pokemonUp, pokemonDown));

                return null;
            }
        };

        new Thread(apiTask).start();
    }

    private int incrementId(int id) {
        id++;
        if (id > Pokemon.getMaxId())
            id = 1;
        return id;
    }

    private int decrementId(int id) {
        id--;
        if (id < 1)
            id = Pokemon.getMaxId();
        return id;
    }

    @Subscribe
    public void onUpAndDownPokemonFetched(FetchUpAndDownPokemonDataEvent event) {
        controllerUp.setData(event.up());
        controllerDown.setData(event.down());
    }

    @Subscribe
    public void onSelectorMoved(SelectorMovedEvent event) {
        if (!event.movedUp()) {
            controllerDown.setData(event.newPokemon());
            return;
        }
        controllerUp.setData(event.newPokemon());
    }
}
