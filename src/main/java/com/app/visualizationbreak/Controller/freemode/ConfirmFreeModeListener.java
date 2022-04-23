package com.app.visualizationbreak.Controller.freemode;

import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.View.scenes.GameScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

public class ConfirmFreeModeListener implements EventHandler<ActionEvent> {
    private final int mapNumber ;
    public ConfirmFreeModeListener(int mapNumber) {
    this.mapNumber = mapNumber;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            GameLauncher.getStage().setScene(GameScene.getInstance("freemode"+ mapNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
