package pl.kk.quizmon.models;

import pl.kk.quizmon.services.PokeApiService;

public class PokemonDAO {
    private final int id;
    private final String name;

    public PokemonDAO(Pokemon pokemon) {
        id = pokemon.getId();
        name = pokemon.getName();
    }

    public PokemonDAO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Pokemon getPokemon() {
        Pokemon pokemon = new Pokemon(id, name);
        PokeApiService service = PokeApiService.getInstance();
        pokemon.setSprite(service.downloadSpriteFromId(pokemon.getId()));
        pokemon.setIcon(service.downloadIconFromId(pokemon.getId()));
        return pokemon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
