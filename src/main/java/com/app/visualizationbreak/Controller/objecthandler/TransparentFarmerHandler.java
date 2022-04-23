package com.app.visualizationbreak.Controller.objecthandler;

import com.app.visualizationbreak.Model.figures.Drawing;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.View.panel.GamePlay;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

import static javafx.scene.paint.Color.INDIANRED;

public class TransparentFarmerHandler implements EventHandler<MouseEvent> {
    private final Drawing drawing ;
    private final String type ;
    private final int tileSize ;

    public TransparentFarmerHandler(String type) throws IOException {
        this.type = type ;
        this.drawing = GamePlay.getInstance().getDrawing() ;
        tileSize = Party.getInstance().getTilesSize() ;
    }

    @Override
    public void handle(MouseEvent event) {
        Rectangle transparentFarmer = new Rectangle(tileSize, tileSize);
        switch (type) {
            case "money":
                transparentFarmer.setStroke(INDIANRED);
                break;
        }
        transparentFarmer.setFill(null);
        try {
            GamePlay.getInstance().setTransFarmer(transparentFarmer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawing.getChildren().add(transparentFarmer);
    }
}
