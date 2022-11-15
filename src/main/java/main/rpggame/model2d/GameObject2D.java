package main.rpggame.model2d;

import javafx.scene.shape.Rectangle;

public class GameObject2D extends Rectangle {

    public GameObject2D(double width, double height, double X, double Y) {
        this.setWidth(width);
        this.setHeight(height);
        this.setX(X);
        this.setY(Y);
    }
}
