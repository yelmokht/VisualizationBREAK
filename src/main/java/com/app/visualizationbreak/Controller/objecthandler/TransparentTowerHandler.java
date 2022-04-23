package com.app.visualizationbreak.Controller.objecthandler;

import com.app.visualizationbreak.Model.figures.Drawing;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.View.panel.GamePlay;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.*;

public class TransparentTowerHandler implements EventHandler<MouseEvent> {
    private final GamePlay gP ;
    private final Drawing drawing ;
    private final String type ;
    private final int tileSize ;
    public TransparentTowerHandler(GamePlay gP, String type){
        this.gP = gP ;
        this.type = type ;
        this.drawing = gP.getDrawing() ;
        tileSize = Party.getInstance().getTilesSize() ;
    }

    @Override
    public void handle(MouseEvent event) {
        Rectangle transparentTower = new Rectangle(tileSize, tileSize);
        Circle towerRange = new Circle() ;
        switch (type) {
            case "classical":
                transparentTower.setStroke(BROWN);
                towerRange.setRadius(120);
                break;
            case "slow":
                transparentTower.setStroke(BLUEVIOLET);
                towerRange.setRadius(70);
                break;
            case "fast":
                transparentTower.setStroke(ORANGE);
                towerRange.setRadius(70);
                break;
            case "sniper":
                transparentTower.setStroke(BLACK);
                towerRange.setRadius(250);
                break;
            case "slowing":
                transparentTower.setStroke(DARKGOLDENROD);
                towerRange.setRadius(200);
                break;
        }
        towerRange.setStroke(BLACK);
        towerRange.setFill(null);
        transparentTower.setFill(null);
        gP.setTransTower(transparentTower);
        gP.setTowerRange(towerRange) ;
        drawing.add(transparentTower, towerRange);
    }
}
