package pl.kk.quizmon.events;

import pl.kk.quizmon.models.Pokemon;

public class FetchUpAndDownPokemonDataEvent {
    private final Pokemon up, down;

    public FetchUpAndDownPokemonDataEvent(Pokemon upPokemon, Pokemon downPokemon) {
        up = upPokemon;
        down = downPokemon;
    }

    public Pokemon getUp() {
        return up;
    }

    public Pokemon getDown() {
        return down;
    }
}
