package pl.kk.quizmon.events;

import pl.kk.quizmon.models.Pokemon;

import java.util.List;

public class FavouriteListGetEvent {
    private final List<Pokemon> list;

    public FavouriteListGetEvent(List<Pokemon> list) {
        this.list = list;
    }

    public List<Pokemon> getList() {
        return list;
    }
}
