package com.objectfab;

import com.objectfab.model.API;
import com.objectfab.model.Model;
import com.objectfab.view.GridView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;

/**
 * Created by synopia on 30.05.2015.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        API api = new API();
        GridView view = new GridView(api, 10,10);
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
