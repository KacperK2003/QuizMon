package pl.kk.quizmon.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class PokeApiService {
    private static final String urlName = "https://pokeapi.co/api/v2/pokemon/scizor"; //Test with scizor

    public PokeApiService() {}

    public String getData() throws MalformedURLException, URISyntaxException, IOException {
        URL url = new URI(urlName).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        conn.disconnect();

        return content.toString();
    }
}
