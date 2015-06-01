package com.objectfab.view;

import com.objectfab.model.API;
import com.objectfab.model.Model;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;

/**
 * Created by synopia on 30.05.2015.
 */
public class GridView extends Region {
    public static final int SCALE = 16;
    private int width;
    private int height;
    private Cell[] map;
    private API api;
    private Group cells = new Group();
    private Group waypoints = new Group();
    private Group paths = new Group();

    public GridView(API api, int width, int height) {
        this.api = api;

        api.updateEvents().subscribe(output -> {
            paths.getChildren().clear();
            for (Model.Point point : output.getPath()) {
                paths.getChildren().add(new Path(point.getX(), point.getY(), SCALE, SCALE));
            }
        });

        this.width = width;
        this.height = height;
        map = new Cell[width*height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = new Cell(x, y, SCALE, SCALE);
                map[y*width+x] = cell;
                cells.getChildren().add(cell);

                cell.setOnMousePressed(evt -> {
                    if (evt.isShiftDown()) {
                        setBlock(cell.getX(), cell.getY(), evt.getButton() == MouseButton.PRIMARY);
                    }
                });
                cell.setOnMouseDragged(evt -> {
                    if (evt.isShiftDown() ) {
                        int currX = (int) (evt.getSceneX() / SCALE);
                        int currY = (int) (evt.getSceneY() / SCALE);
                        setBlock(currX, currY, evt.getButton() == MouseButton.PRIMARY);
                    }
                });
                cell.setOnMouseClicked(evt -> {
                    addWayPoint(cell);
                });
            }
        }

        getChildren().add(cells);
        getChildren().add(paths);
        getChildren().add(waypoints);
    }

    public void addWayPoint(Cell cell) {
        Waypoint waypoint = new Waypoint(cell.getX(), cell.getY(), SCALE, SCALE);
        waypoints.getChildren().add(waypoint);

        update();
    }

    public void setBlock( int x, int y, boolean wall ) {
        map[x+y*width].setWall(wall);

        update();
    }

    private void update() {
        Model model = new Model(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                model.setWall(x, y, map[x+y*width].isWall());
            }
        }
        for (Node node : waypoints.getChildren()) {
            Waypoint waypoint = (Waypoint) node;
            model.addWaypoint(waypoint.getX(), waypoint.getY());
        }
        api.update(model);
    }
}
