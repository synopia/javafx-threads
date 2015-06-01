package com.objectfab.model;

import com.objectfab.model.pathfinder.AStar;
import com.objectfab.model.pathfinder.BitMap;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.reactfx.EventStream;
import org.reactfx.EventStreams;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by synopia on 29.05.2015.
 */
public class API {
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private EventStream<Model> out;
    private ObjectProperty<Model> model = new SimpleObjectProperty<>();

    public API() {
        out = EventStreams
                .changesOf(model)
                .reduceSuccessions((a, b) -> b, Duration.ofMillis(200), executor, executor)
                .map(m -> process(m.getNewValue()))
                .threadBridgeToFx(executor);
    }

    public EventStream<Model> updateEvents() {
        return out;
    }

    public void update(Model newModel) {
        this.model.set(newModel);
    }

    protected Model process(Model input) {
        System.out.println("Processing in thread " + Thread.currentThread());
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
