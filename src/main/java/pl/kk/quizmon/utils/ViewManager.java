package pl.kk.quizmon.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import pl.kk.quizmon.QuizMonApplication;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public final class ViewManager {
    private static ViewManager instance = null;
    private Scene scene = null;

    private ViewManager() {}

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
            Parent root = FXMLLoader.load(Objects.requireNonNull(QuizMonApplication.class.getResource(view.getFileName())));
            scene.setRoot(root);
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
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
