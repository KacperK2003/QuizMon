package pl.kk.quizmon.controllers;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import pl.kk.quizmon.events.QuizQuestionPreparedEvent;
import pl.kk.quizmon.infrastructure.EventBusProvider;
import pl.kk.quizmon.infrastructure.ViewManager;
import pl.kk.quizmon.models.Pokemon;
import pl.kk.quizmon.services.PokeApiService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class QuizController extends LifetimeController {
    @FXML
    private Label label;
    @FXML
    private ImageView pokemonImage;
    @FXML
    private Button button1, button2, button3;
    @FXML
    private Label pointLabel;

    private Pokemon correctPokemon;
    private int points;
    private final String text = "Co to za pokemon?";

    private static void getQuestion() {
        List<Integer> ids = getThreeRandomIds();
        Pokemon[] pokemons = new Pokemon[3];

        try {
            for (int i = 0; i < 3; i++)
                pokemons[i] = PokeApiService.getInstance().getPokemonData(Integer.toString(ids.get(i)));
        } catch (IOException | URISyntaxException e) {
            Logger.getGlobal().severe(e.getMessage());
        }

        EventBusProvider.getEventBus().post(new QuizQuestionPreparedEvent(pokemons));
    }

    private static List<Integer> getThreeRandomIds() {
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= Pokemon.getMaxId(); i++)
            nums.add(i);

        Collections.shuffle(nums);
        return nums.subList(0, 3);
    }

    @FXML
    protected void onBackButtonClick() {
        ViewManager.getInstance().switchView(ViewManager.View.MainMenu);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        points = 0;
        pointLabel.setText("0");
        button1.setDisable(false);
        button2.setDisable(false);
        button3.setDisable(false);
        label.setText(text);

        prepareQuestion(true);
    }

    private void prepareQuestion(boolean isOnLoad) {
        Task<Void> apiTask = new Task<>() {
            @Override
            protected Void call() {
                if (!isOnLoad) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Logger.getGlobal().severe(e.getMessage());
                    }
                }
                getQuestion();
                return null;
            }
        };

        new Thread(apiTask).start();
    }

    @Subscribe
    public void onQuizQuestionPrepered(QuizQuestionPreparedEvent event) {
        Platform.runLater(() -> {
            Pokemon[] pokemons = event.getPokemons();
            Random random = new Random();
            int correct = random.nextInt(0, 3);

            pokemonImage.setImage(pokemons[correct].getSprite());
            correctPokemon = pokemons[correct];

            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.brightnessProperty().set(-1.0);
            pokemonImage.setEffect(colorAdjust);

            button1.setText(pokemons[0].getName());
            button2.setText(pokemons[1].getName());
            button3.setText(pokemons[2].getName());
            button1.setDisable(false);
            button2.setDisable(false);
            button3.setDisable(false);
            label.setText(text);
        });
    }

    @FXML
    protected void onAnswerClick(Event event) {
        if (event.getSource() instanceof Button button) {
            String name = button.getText();

            button1.setDisable(true);
            button2.setDisable(true);
            button3.setDisable(true);

            label.setText("To " + correctPokemon.getName() + "!");

            if (name.equals(correctPokemon.getName())) {
                points++;
                pointLabel.setText(Integer.toString(points));
            }

            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.brightnessProperty().set(0.0);
            pokemonImage.setEffect(colorAdjust);

            prepareQuestion(false);
        }
    }
}
