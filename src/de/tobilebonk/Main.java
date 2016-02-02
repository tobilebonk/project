package de.tobilebonk;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Dappsen on 16.01.2016.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        SuperController sc = new SuperController();

        primaryStage.setTitle("RNA Viewer --- Give him a good mark! - Edition");
        primaryStage.setScene(sc.getSuperView().getScene());
        primaryStage.sizeToScene();
        primaryStage.show();

    }
}
