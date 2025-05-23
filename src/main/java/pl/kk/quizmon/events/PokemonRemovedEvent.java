package pl.kk.quizmon.events;

import pl.kk.quizmon.controllers.components.FavouritePokemonViewerController;

public class PokemonRemovedEvent {
    private FavouritePokemonViewerController sender;

    public PokemonRemovedEvent(FavouritePokemonViewerController sender) {
        this.sender = sender;
    }

    public FavouritePokemonViewerController getSender() {
        return sender;
    }
}