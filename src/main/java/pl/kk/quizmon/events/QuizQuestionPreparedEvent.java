package pl.kk.quizmon.events;

import pl.kk.quizmon.models.Pokemon;

public class QuizQuestionPreparedEvent {
    Pokemon[] pokemons;

    public QuizQuestionPreparedEvent(Pokemon[] pokemons) {
        this.pokemons = pokemons;
    }

    public Pokemon[] getPokemons() {
        return pokemons;
    }
}
