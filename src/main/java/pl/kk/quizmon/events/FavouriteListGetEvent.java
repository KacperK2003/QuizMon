package pl.kk.quizmon.events;

import pl.kk.quizmon.models.Pokemon;

import java.util.List;

public record FavouriteListGetEvent(List<Pokemon> list) { }
