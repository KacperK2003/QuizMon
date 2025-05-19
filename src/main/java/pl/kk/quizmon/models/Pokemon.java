package pl.kk.quizmon.models;

import javafx.scene.image.Image;

public class Pokemon {
    private final int id;
    private final String name;
    private Image sprite;
    private Image icon;
    private static final Pokemon unknown = new Pokemon(0, "???");
    private static final int maxId = 898;

    public Pokemon(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Image getSprite() {
        return sprite;
    }

    public Image getIcon() {
        return icon;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public static Pokemon getUnknown() {
        return unknown;
    }
    public static int getMaxId() {
        return maxId;
    }
}
