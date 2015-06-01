package com.objectfab.model;

import com.objectfab.model.pathfinder.AStar;
import com.objectfab.model.pathfinder.BitMap;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by synopia on 29.05.2015.
 */
public class API {
    private ExecutorService executor = Executors.newFixedThreadPool(1);

    public API() {

    }

    public CompletableFuture<Model> update(Model model) {
        CompletableFuture<Model> future = new CompletableFuture<>();
        executor.execute(() -> {
            future.complete(process(model));
        });
        return future;
    }

    protected Model process(Model input) {
        BitMap map = new BitMap(input.width, input.height) {
            @Override
            public boolean isPassable(int offset) {
                return !input.map[offset];
            }
        };
        AStar aStar = new AStar(map);
        Model.Point last = null;
        for (Model.Point point : input.waypoints) {
            if( last!=null ) {
                aStar.reset();
                boolean found = aStar.run(last.getX() + last.getY() * input.width, point.getX() + point.getY() * input.width);
                if( found ) {
                    for (Integer offset : aStar.getPath()) {
                        input.path.add(new Model.Point(offset%input.width, offset/input.width));
                    }
                }
            }
            last = point;
        }
        return input;
    }
}
