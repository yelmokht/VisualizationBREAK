package com.app.visualizationbreak.Controller.objecthandler;

import com.app.visualizationbreak.Model.figures.Drawing;
import com.app.visualizationbreak.View.panel.GamePlay;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ObjectModeHandler implements EventHandler<MouseEvent> {
    private Drawing drawing ;
    public ObjectModeHandler(){
    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getButton() == MouseButton.SECONDARY){
            try {
                GamePlay.getInstance().exitAllModes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
