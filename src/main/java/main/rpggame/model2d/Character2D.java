package main.rpggame.model2d;

import javafx.scene.image.Image;
import main.rpggame.characters.Character;
import main.rpggame.Direction;

public class Character2D extends GameObject2D{
    Image characterImage;
    private Direction direction;
    private Character info;

    public Character2D(Image image, double width, double height, double X, double Y, double velocity, Character info) {
        super(width, height, X, Y);
        this.characterImage = image;
        this.direction = Direction.getNewDirection();
        this.info = info;
    }

    public Image getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(Image characterImage) {
        this.characterImage = characterImage;
    }

    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void moveLeft() {
        this.setX(this.getX()-info.getVelocity());
    }

    public void moveRight() {
        this.setX(this.getX()+info.getVelocity());
    }

    public void moveUp() {
        this.setY(this.getY()-info.getVelocity());
    }

    public void moveDown() {
        this.setY(this.getY()+info.getVelocity());
    }

    @Override
    public String toString() {
        return info.getName() + " " + this.getX() + " " + this.getY();
    }

    public Character getInfo() {
        return this.info;
    }

    public void setInfo(Character info) {
        this.info = info;
    }

}
