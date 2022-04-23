package com.app.visualizationbreak.Controller.objecthandler;

import com.app.visualizationbreak.View.panel.GamePlay;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class TowerHandler implements EventHandler<MouseEvent> {
    private final String type ;

    public TowerHandler(String type) {
        this.type = type ;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            GamePlay.getInstance().enterTowerMode(type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
