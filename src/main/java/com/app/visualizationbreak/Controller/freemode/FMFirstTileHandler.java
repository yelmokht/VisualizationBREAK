package com.app.visualizationbreak.Controller.freemode;

import com.app.visualizationbreak.View.panel.FreeModeDrawing;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class FMFirstTileHandler implements EventHandler<MouseEvent> {
    private final FreeModeDrawing drawingMode ;
    private final Rectangle firstTile ;
    private final int tileSize ;
    private final ArrayList<ArrayList<Rectangle>> tileList ;

    public FMFirstTileHandler(FreeModeDrawing drawingMode, Rectangle firstTile){
        this.drawingMode = drawingMode ;
        this.firstTile = firstTile ;
        this.tileList = drawingMode.getTileList();
        this.tileSize = drawingMode.getTilesSize() ;
    }
    @Override
    public void handle(MouseEvent event) {
        // on localise la premiere case du chemin
        drawingMode.setFirstTileLoc((int)firstTile.getY()/tileSize);
        for(ArrayList<Rectangle> line : tileList){
            for(Rectangle r : line){
                r.setOnMouseClicked(null);
            }
        }
        firstTile.setFill(Color.SLATEGREY);
        firstTile.setOpacity(1);
        // on démarre l'écriture du chemin à l'aide des flèches
        drawingMode.getFMScene().setOnKeyPressed(new FMDrawingHandler(drawingMode));
    }
}
