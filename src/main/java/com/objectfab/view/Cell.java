package com.objectfab.view;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by synopia on 30.05.2015.
 */
public class Cell extends Region {
    private int x;
    private int y;
    private BooleanProperty wall = new SimpleBooleanProperty();
    private Rectangle background;

    public Cell(int x, int y, double width, double height) {
        this.x = x;
        this.y = y;

        setLayoutX(x*width);
        setLayoutY(y * height);
        setWidth(width);
        setHeight(height);
        background = new Rectangle();
        background.setWidth(width);
        background.setHeight(height);
        getChildren().add(background);
        background.fillProperty().bind(Bindings.when(wall).then(Color.BLACK).otherwise(Color.WHITE));
    }

    public void setWall( boolean wall ) {
        this.wall.setValue(wall);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWall() {
        return wall.get();
    }
}
