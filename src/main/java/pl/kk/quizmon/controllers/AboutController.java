package pl.kk.quizmon.controllers;

import javafx.fxml.FXML;
import pl.kk.quizmon.QuizMonApplication;
import pl.kk.quizmon.utils.ViewManager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.logging.Logger;

public class AboutController {
    @FXML
    protected void onBackButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.MainMenu);
    }

    @FXML
    protected void onOLFButtonClick() {
        try {
            URI uri = Objects.requireNonNull(
                    QuizMonApplication.class.getResource("fonts/Audiowide/OFL.txt")
            ).toURI();
            File file = new File(uri);
            Desktop.getDesktop().open(file);
        } catch (IOException | URISyntaxException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }
}
