package com.app.visualizationbreak.Model;

import com.app.visualizationbreak.Model.gameorganisator.Game;
import com.app.visualizationbreak.View.scenes.MenuScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameLauncher extends Application {
    private static Stage stage;
    private static final Scene scene = MenuScene.getInstance();
    private final String stageTitle = "VisualizationBreak";


    @Override
    public void start(Stage stage) throws Exception {
        GameLauncher.stage = stage;
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        Game.getInstance();
        stage.setFullScreen(true);
        stage.show();

    }

    public void stop() throws Exception {
        System.out.println("Successful end operation");
    }

    public static Stage getStage() { return stage; }

    public static Scene getScene() { return scene; }

    public static void main(String[] args) {
        launch(args);
    }
}

