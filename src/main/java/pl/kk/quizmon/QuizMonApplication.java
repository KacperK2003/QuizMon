package pl.kk.quizmon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;
import java.util.logging.Logger;

public class QuizMonApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Configuration config = prepareConfig();

        int width = config.getInt("app.width");
        int height = config.getInt("app.height");
        String title = config.getString("app.title");

        FXMLLoader fxmlLoader = new FXMLLoader(QuizMonApplication.class.getResource("views/mainmenu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public Configuration prepareConfig() {
        Configurations configurations = new Configurations();
        try {
            return configurations.properties("pl/kk/quizmon/config.properties");
        } catch (ConfigurationException e) {
            Logger.getGlobal().severe(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}