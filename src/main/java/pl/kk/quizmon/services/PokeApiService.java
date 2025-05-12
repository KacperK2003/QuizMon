package pl.kk.quizmon.services;

import com.google.gson.Gson;
import pl.kk.quizmon.models.Pokemon;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

public class PokeApiService {
    private static final String urlName = "https://pokeapi.co/api/v2/pokemon/";
    private static final String urlSpriteName = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    private static final String urlIconName = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-viii/icons/";

    private static PokeApiService instance;

    private PokeApiService() {}

    private record PokemonApiResponse(int id, String name) { }

    public Pokemon getPokemonData(String name) throws URISyntaxException, IOException {
        name = name.toLowerCase();
        String jsonData = downloadJsonData(name);
        Gson gson = new Gson();
        PokemonApiResponse apiResponse = gson.fromJson(jsonData, PokemonApiResponse.class);

        Pokemon pokemon = new Pokemon(apiResponse.id(), apiResponse.name());
        pokemon.setSprite(downloadImageData(urlSpriteName + pokemon.getId() + ".png"));
        pokemon.setIcon(downloadImageData(urlIconName + pokemon.getId() + ".png"));

        return pokemon;
    }

    public Pokemon getPokemonData(int id) throws URISyntaxException, IOException {
        String jsonData = downloadJsonData(Integer.toString(id));
        Gson gson = new Gson();
        PokemonApiResponse apiResponse = gson.fromJson(jsonData, PokemonApiResponse.class);

        Pokemon pokemon = new Pokemon(apiResponse.id(), apiResponse.name());
        pokemon.setSprite(downloadImageData(urlSpriteName + pokemon.getId() + ".png"));
        pokemon.setIcon(downloadImageData(urlIconName + pokemon.getId() + ".png"));

        return pokemon;
    }

    public static PokeApiService getInstance() {
        if (instance == null)
            instance = new PokeApiService();

        return instance;
    }

    private String downloadJsonData(String name) throws URISyntaxException, IOException {
        URL url = new URI(urlName + name).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        conn.disconnect();

        return content.toString();
    }

    private Image downloadImageData(String url) {
        Image image = new Image(url, true);

        if (image.isError()) {
            Logger.getGlobal().severe("Failed to download sprite!");
            Logger.getGlobal().severe(image.getException().getMessage());
        }

        return image;
    }
}
