package pl.kk.quizmon;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import pl.kk.quizmon.utils.ViewManager;

import java.io.IOException;
import java.util.logging.Logger;

public class QuizMonApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Configuration config = prepareConfig();

        if (config == null) {
            Logger.getGlobal().severe("Failed to load configuration file!");
            Platform.exit();
            return;
        }

        int width = config.getInt("app.width");
        int height = config.getInt("app.height");
        String title = config.getString("app.title");

        Scene scene = prepareMainMenuScene(width, height);

        ViewManager.getInstance().setScene(scene);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private Configuration prepareConfig() {
        Configurations configurations = new Configurations();
        try {
            return configurations.properties("pl/kk/quizmon/config.properties");
        } catch (ConfigurationException e) {
            Logger.getGlobal().severe(e.getMessage());
            return null;
        }
    }

    private Scene prepareMainMenuScene(int width, int height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                ViewManager.View.MainMenu.getFileName()
        ));
        return new Scene(fxmlLoader.load(), width, height);
    }

    public static void main(String[] args) {
        launch();
    }
}