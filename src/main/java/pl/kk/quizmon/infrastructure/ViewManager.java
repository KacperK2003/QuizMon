package pl.kk.quizmon.infrastructure;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import pl.kk.quizmon.QuizMonApplication;
import pl.kk.quizmon.controllers.LifetimeController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public final class ViewManager {
    private static ViewManager instance = null;
    private View currentView;
    private Scene scene = null;
    private final Map<View, ViewData> cachedViews;

    private ViewManager() {
        currentView = View.MainMenu;
        cachedViews = new HashMap<>();
    }

    public enum View {
        MainMenu("views/mainmenu-view.fxml"),
        Pokedex("views/pokedex-view.fxml"),
        About("views/about-view.fxml");

        private final String fileName;
        View(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    private record ViewData(Parent root, Object controller) { }

    public void switchView(View view) {
        if (scene == null) {
            Logger.getGlobal().severe("Switching View failed - the scene is null!");
            return;
        }

        try {
            ViewData viewData = getViewData(view);

            if (currentView != null) {
                ViewData oldViewData = getViewData(currentView);
                if (oldViewData.controller() instanceof LifetimeController lifetimeController)
                    lifetimeController.onUnload();
            }

            currentView = view;
            scene.setRoot(viewData.root());

            if (viewData.controller() instanceof LifetimeController lifetimeController)
                lifetimeController.onLoad();

        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

    private ViewData getViewData(View view) throws IOException {
        if (cachedViews.containsKey(view)) {
            Logger.getGlobal().info("Loading view from cache.");
            return cachedViews.get(view);
        }

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(QuizMonApplication.class.getResource(view.getFileName())));
        loader.load();
        Parent root = loader.getRoot();
        Object controller = loader.getController();

        ViewData viewData = new ViewData(root, controller);

        cachedViews.put(view, viewData);

        return viewData;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public static ViewManager getInstance() {
        if (instance == null)
            instance = new ViewManager();

        return instance;
    }
}
