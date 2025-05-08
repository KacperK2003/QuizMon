package pl.kk.quizmon.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import pl.kk.quizmon.QuizMonApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public final class ViewManager {
    private static ViewManager instance = null;
    private Scene scene = null;
    private Map<View, Parent> cachedViews;

    private ViewManager() {
        cachedViews = new HashMap<>();
    }

    public enum View {
        MainMenu("views/mainmenu-view.fxml"),
        About("views/about-view.fxml");

        private final String fileName;
        View(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    public void switchView(View view) {
        if (scene == null) {
            Logger.getGlobal().severe("Switching View failed - the scene is null!");
            return;
        }

        try {
            Parent root = getRootOfView(view);
            scene.setRoot(root);
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

    private Parent getRootOfView(View view) throws IOException {
        if (cachedViews.containsKey(view)) {
            Logger.getGlobal().info("Loading view from cache.");
            return cachedViews.get(view);
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(QuizMonApplication.class.getResource(view.getFileName())));
        cachedViews.put(view, root);

        return root;
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
