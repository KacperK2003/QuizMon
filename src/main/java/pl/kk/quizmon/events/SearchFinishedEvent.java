package pl.kk.quizmon.events;

import pl.kk.quizmon.models.Pokemon;

public class SearchFinishedEvent {
    Pokemon result;

    public SearchFinishedEvent(Pokemon result) {
        this.result = result;
    }

    public Pokemon getResult() {
        return result;
    }
}
