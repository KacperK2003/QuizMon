package pl.kk.quizmon.events;

import pl.kk.quizmon.controllers.components.FavouritePokemonViewerController;

public record PokemonRemovedEvent(FavouritePokemonViewerController sender) { }