package main.rpggame.model2d;

import javafx.scene.shape.Rectangle;

public class GameObject2D extends Rectangle {
    private String name;
    private double velocity = 0;

    public GameObject2D(String name, double width, double height, double X, double Y) {
        this.name = name;
        this.setWidth(width);
        this.setHeight(height);
        this.setX(X);
        this.setY(Y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
}
