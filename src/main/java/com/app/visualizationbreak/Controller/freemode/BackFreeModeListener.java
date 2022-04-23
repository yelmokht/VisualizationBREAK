package com.app.visualizationbreak.Controller.freemode;

import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.View.scenes.MenuScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class BackFreeModeListener implements EventHandler<ActionEvent> {
    public BackFreeModeListener(){

    }

    @Override
    public void handle(ActionEvent event) {
        GameLauncher.getStage().setScene(MenuScene.getInstance()) ;
    }
}
