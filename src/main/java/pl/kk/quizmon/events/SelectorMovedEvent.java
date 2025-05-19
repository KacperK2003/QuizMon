package pl.kk.quizmon.events;

import pl.kk.quizmon.models.Pokemon;

public class SelectorMovedEvent {
    private final Pokemon newPokemon;
    private final Pokemon newMiddlePokemon;
    private final boolean movedUp;

    public SelectorMovedEvent(Pokemon newPokemon, Pokemon newMiddlePokemon, boolean movedUp) {
        this.newPokemon = newPokemon;
        this.newMiddlePokemon = newMiddlePokemon;
        this.movedUp = movedUp;
    }

    public Pokemon getNewPokemon() {
        return newPokemon;
    }

    public Pokemon getNewMiddlePokemon() {
        return newMiddlePokemon;
    }

    public boolean getMovedUp() {
        return movedUp;
    }
}
