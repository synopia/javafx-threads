package com.objectfab.view;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by synopia on 30.05.2015.
 */
public class Waypoint extends Region {
    private int x;
    private int y;
    private Circle background;

    public Waypoint(int x, int y, double width, double height) {
        this.x = x;
        this.y = y;

        setLayoutX((x+0.5) * width);
        setLayoutY((y+0.5) * height);
        setWidth(width);
        setHeight(height);
        background = new Circle();
        background.setRadius(width / 3);
        getChildren().add(background);
        background.setFill(Color.BLUE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
