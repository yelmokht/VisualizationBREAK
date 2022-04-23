package com.app.visualizationbreak.Model.factories;

import com.app.visualizationbreak.Model.figures.PNJ;
import com.app.visualizationbreak.Model.gameorganisator.Wave;
import javafx.scene.paint.Color;

public class PNJFactory {
    public static PNJ getInstance(char type, Wave currentWave, int pathNumber){
        PNJ pnj = null ;
        switch (type){
            case 'A' : pnj = new PNJ(2, Color.DARKORANGE, 9, 40, 20, currentWave, pathNumber) ; break;
            case 'B' : pnj = new PNJ(3, Color.ALICEBLUE, 7, 30, 15, currentWave, pathNumber) ; break;
            case 'C' : pnj = new PNJ(4, Color.LIGHTGREEN, 6, 20, 10, currentWave, pathNumber) ; break;
            case 'D' : pnj = new PNJ(5, Color.MAGENTA, 5, 10, 10, currentWave, pathNumber) ; break;
        }
        return pnj ;
    }
}
