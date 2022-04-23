package com.app.visualizationbreak.Controller;

import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.View.scenes.GameScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

public class LevelSelectListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        try {
            GameLauncher.getStage().setScene(GameScene.getInstance("regular"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
