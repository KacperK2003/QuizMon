package pl.kk.quizmon.models;

import javafx.scene.image.Image;

public class Pokemon {
    private final int id;
    private final String name;
    private Image sprite;
    private Image icon;

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
}
