package com.app.visualizationbreak.Controller.objecthandler;

import com.app.visualizationbreak.View.panel.GamePlay;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FarmerHandler implements EventHandler<MouseEvent> {
    private final String type ;

    public FarmerHandler(String type) {
        this.type = type ;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            GamePlay.getInstance().enterFarmerMode(type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}