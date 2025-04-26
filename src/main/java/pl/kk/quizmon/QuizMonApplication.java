package pl.kk.quizmon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.kk.quizmon.utils.ConfigLoader;

import java.io.IOException;

public class QuizMonApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ConfigLoader config = ConfigLoader.getInstance();
        int width = Integer.parseInt(config.getProperty("app.width"));
        int height = Integer.parseInt(config.getProperty("app.height"));
        String title = config.getProperty("app.title");

        FXMLLoader fxmlLoader = new FXMLLoader(QuizMonApplication.class.getResource("views/mainmenu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}