package com.objectfab.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by synopia on 29.05.2015.
 */
public class Model {
    public static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
    int width;
    int height;

    boolean map[];
    List<Point> waypoints = new ArrayList<>();
    List<Point> path = new ArrayList<>();

    public Model(int width, int height) {
        this.width = width;
        this.height = height;

        map = new boolean[width*height];
    }

    public void addWaypoint( int x, int y) {
        waypoints.add(new Point(x,y));
    }

    public List<Point> getPath() {
        return path;
    }

    public void setWall(int x, int y, boolean wall) {
        map[x+y*width] = wall;
    }

    public boolean isWall(int x, int y) {
        return map[x+y*width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
