package pl.kk.quizmon.controllers;

import pl.kk.quizmon.infrastructure.EventBusProvider;

public abstract class LifetimeController {
    public void onLoad() {
        EventBusProvider.getEventBus().register(this);
    }
    public void onUnload() {
        EventBusProvider.getEventBus().unregister(this);
    }
}
