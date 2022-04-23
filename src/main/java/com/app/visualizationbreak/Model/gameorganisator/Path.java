package com.app.visualizationbreak.Model.gameorganisator;


import com.app.visualizationbreak.Model.figures.Point;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Path {
    private final int currentLevelNumber ;
    private File myMap ;
    private final String pathSequence ;
    private final int tileWidth;
    private final int pathNumber ;
    private final Level level ;
    private final Point originPoint ;

    public Path(Level level, int pathNumber) throws IOException {
        this.currentLevelNumber = level.getNumber() ;
        this.pathNumber = pathNumber ;
        this.level = level ;
        tileWidth = level.getParty().getTilesSize() ;
        this.pathSequence = this.level.getPathSequences().get(pathNumber) ;
        this.originPoint = new Point(0, tileWidth*level.firstTileLoc().get(pathNumber) + tileWidth/2) ;
    }

    // crée une liste de points à partir d'une séquence en String de style "RRRRDDDRRRUUUURR"
    public ArrayList<Point> analyseSequence(){
        ArrayList<Point> pointsList = new ArrayList<>() ;
        int i = 0;
        char[]ps = pathSequence.toUpperCase().toCharArray();
        double x = originPoint.getX()+tileWidth/2 ;
        double y = originPoint.getY() ;
        pointsList.add(new Point(x,y)) ;

        while(i<ps.length-1){
            int it = 1 ;
            while(i!=ps.length-1 && ps[i] == ps[i+1]){
                it +=1 ;
                i++;
            }
            char charatI = ps[i];
            if(charatI == 'R'){
                x+= it*tileWidth;
            }
            else if(charatI == 'L'){
                x-= it*tileWidth ;
            }
            else if(charatI == 'U'){
                y-=it*tileWidth ;
            }
            else if(charatI == 'D'){
                y+= it*tileWidth ;
            }
            pointsList.add(new Point(x,y));
            i++;
        }

        return pointsList;
    }



}
