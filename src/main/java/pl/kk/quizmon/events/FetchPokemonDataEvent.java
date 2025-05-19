package pl.kk.quizmon.events;

import pl.kk.quizmon.models.Pokemon;

public class FetchPokemonDataEvent {
    Pokemon result;

    public FetchPokemonDataEvent(Pokemon result) {
        this.result = result;
    }

    public Pokemon getResult() {
        return result;
    }
}