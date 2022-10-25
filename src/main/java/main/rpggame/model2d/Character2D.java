package main.rpggame.model2d;

import javafx.scene.image.Image;

public class Character2D extends GameObject2D{
    Image characterImage;

    public Character2D(Image image, String name, double width, double height, double X, double Y, double velocity) {
        super(name, width, height, X, Y);
        this.characterImage = image;
        setVelocity(velocity);
    }

    public Image getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(Image characterImage) {
        this.characterImage = characterImage;
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getX() + " " + this.getY();
    }
}
