package pl.kk.quizmon.events;

import pl.kk.quizmon.models.Pokemon;

public record SelectorMovedEvent(Pokemon newPokemon, Pokemon newMiddlePokemon, boolean movedUp) { }
