package com.app.visualizationbreak.Controller.freemode;

import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.View.panel.FreeModeLevelSelect;
import javafx.event.Event;
import javafx.event.EventHandler;

public class FMLevelSelectListener implements EventHandler {

    public FMLevelSelectListener(){

    }
    @Override
    public void handle(Event event) {
        GameLauncher.getScene().setRoot(new FreeModeLevelSelect());
    }
}
