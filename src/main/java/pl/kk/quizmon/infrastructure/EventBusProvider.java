package pl.kk.quizmon.infrastructure;

import com.google.common.eventbus.EventBus;

public class EventBusProvider {
    private static final EventBus eventBus = new EventBus();

    public static EventBus getEventBus() {
        return eventBus;
    }
}
