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
    private static final String urlName = "https://pokeapi.co/api/v2/pokemon/scizor"; //Test with scizor
    private static final String urlSpriteName = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/212.png";

    public PokeApiService() {}

    private record PokemonApiResponse(int id, String name) { }

    public Pokemon getPokemonData() throws URISyntaxException, IOException {
        String jsonData = downloadJsonData();
        Gson gson = new Gson();
        //Pokemon pokemon = gson.fromJson(jsonData, Pokemon.class); // Tu się pierdoli, pewnie bo jest obrazek a json nie ma obrazka.
        PokemonApiResponse apiResponse = gson.fromJson(jsonData, PokemonApiResponse.class);

        Pokemon pokemon = new Pokemon(apiResponse.id(), apiResponse.name());
        pokemon.setSprite(downloadImageData());

        return pokemon;
    }

    private String downloadJsonData() throws URISyntaxException, IOException {
        URL url = new URI(urlName).toURL();
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

    private Image downloadImageData() throws URISyntaxException, IOException {
        Image image = new Image(urlSpriteName, true);

        if (image.isError()) {
            System.out.println("Błąd ładowania obrazu: " + image.getException());
        }

        return image;
    }
}
