package pl.kk.quizmon.controllers;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import pl.kk.quizmon.QuizMonApplication;
import pl.kk.quizmon.controllers.components.FavouritePokemonViewerController;
import pl.kk.quizmon.events.FavouriteListGetEvent;
import pl.kk.quizmon.events.PokemonRemovedEvent;
import pl.kk.quizmon.infrastructure.EventBusProvider;
import pl.kk.quizmon.infrastructure.ViewManager;
import pl.kk.quizmon.models.Pokemon;
import pl.kk.quizmon.services.DatabaseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FavouriteController extends LifetimeController {
    @FXML
    private VBox mainVBox;

    private List<Node> viewers;
    private List<FavouritePokemonViewerController> viewersControllers;

    @FXML
    protected void initialize() {
        viewers = new ArrayList<>();
        viewersControllers = new ArrayList<>();
    }

    @Override
    public void onLoad() {
        super.onLoad();

        Task<Void> databaseTask = new Task<Void>() {
            @Override
            protected Void call() {
                List<Pokemon> favouriteList = DatabaseService.getInstance().getPokemonList();
                EventBusProvider.getEventBus().post(new FavouriteListGetEvent(favouriteList));
                return null;
            }
        };

        new Thread(databaseTask).start();
    }

    @Override
    public void onUnload() {
        for (Node node : viewers)
            mainVBox.getChildren().remove(node);
        viewers.clear();
        viewersControllers.clear();
    }

    @FXML
    protected void onBackButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.MainMenu);
    }

    @Subscribe
    public void onFavouriteListGet(FavouriteListGetEvent event) {
        List<Pokemon> favouriteList = event.getList();
        if (favouriteList == null || favouriteList.isEmpty())
            return;

        try {
            for (Pokemon pokemon : favouriteList) {
                FXMLLoader loader = new FXMLLoader(QuizMonApplication.class.getResource("views/components/favourite-pokemon-viewer.fxml"));
                viewers.add(loader.load());
                FavouritePokemonViewerController controller = loader.getController();
                controller.setData(pokemon);
                viewersControllers.add(controller);
            }
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }

        Platform.runLater(() -> {
            for (Node node : viewers)
                mainVBox.getChildren().add(node);
        });
    }

    @Subscribe
    public void onPokemonRemovedEvent(PokemonRemovedEvent event) {
        Platform.runLater(() -> {
            FavouritePokemonViewerController controller = event.getSender();
            int index = viewersControllers.indexOf(controller);

            if (index == -1)
                return;

            viewersControllers.remove(controller);
            Node viewer = viewers.get(index);
            viewers.remove(viewer);

            mainVBox.getChildren().remove(viewer);
        });
    }
}
