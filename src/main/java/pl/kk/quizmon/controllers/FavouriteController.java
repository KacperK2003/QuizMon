package pl.kk.quizmon.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import pl.kk.quizmon.infrastructure.ViewManager;
import pl.kk.quizmon.models.Pokemon;
import pl.kk.quizmon.services.DatabaseService;

import java.util.List;

public class FavouriteController {
    @FXML
    private ImageView test;
    @FXML
    private Label testlabel;

    @FXML
    protected void initialize() {
        List<Pokemon> list = DatabaseService.getInstance().getPokemonList();

        if (list == null)
            return;

        testlabel.setText(list.getFirst().getName());
        test.setImage(list.getFirst().getIcon());
    }

    @FXML
    protected void onBackButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.MainMenu);
    }
}
