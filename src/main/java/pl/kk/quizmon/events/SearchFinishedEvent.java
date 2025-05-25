package pl.kk.quizmon.events;

import pl.kk.quizmon.models.Pokemon;

public record SearchFinishedEvent(Pokemon result) { }
