package pl.kk.quizmon.models;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;

import java.io.ByteArrayInputStream;

public class PokemonDAO {
    private final int id;
    private final String name;
    private final byte[] sprite;
    private final byte[] icon;

    public PokemonDAO(Pokemon pokemon) {
        id = pokemon.getId();
        name = pokemon.getName();
        sprite = imageToBytes(pokemon.getSprite());
        icon = imageToBytes(pokemon.getIcon());
    }

    public PokemonDAO(int id, String name, byte[] sprite, byte[] icon) {
        this.id = id;
        this.name = name;
        this.sprite = sprite;
        this.icon = icon;
    }

    private byte[] imageToBytes(Image image) {
        if (image == null) return null;

        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        final int channels = 4;
        final int offset = 0;
        int stride = width * channels;

        byte[] buffer = new byte[width * height * channels];
        PixelReader pixelReader = image.getPixelReader();
        pixelReader.getPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), buffer, offset, stride);

        return buffer;
    }

    private Image bytesToImage(byte[] bytes) {
        if (bytes == null) return null;

        Image image = new Image(new ByteArrayInputStream(bytes));

        while (image.getProgress() < 1.0 && !image.isError()) {
            try {
                Thread.sleep(10); // nieoptymalne, ale działa
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return image;
    }

    public Pokemon getPokemon() {
        Pokemon pokemon = new Pokemon(id, name);
        pokemon.setSprite(bytesToImage(sprite));
        pokemon.setIcon(bytesToImage(icon));
        return pokemon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getSprite() {
        return sprite;
    }

    public byte[] getIcon() {
        return icon;
    }
}
