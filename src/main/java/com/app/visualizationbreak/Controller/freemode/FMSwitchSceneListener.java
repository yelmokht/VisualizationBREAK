package com.app.visualizationbreak.Controller.freemode;

import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.View.scenes.FreeModeScene;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.io.IOException;

public class FMSwitchSceneListener implements EventHandler {
    private final int mapNumber ;

    public FMSwitchSceneListener(int mapNumber){
        this.mapNumber = mapNumber;
    }

    public FMSwitchSceneListener(){
        this.mapNumber = 666;
    }
    @Override
    public void handle(Event event) {
        try {
            if(mapNumber == 666){
                GameLauncher.getStage().setScene(new FreeModeScene());
            }
            else{
                GameLauncher.getStage().setScene(new FreeModeScene(mapNumber));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
