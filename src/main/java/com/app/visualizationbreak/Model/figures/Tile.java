package com.app.visualizationbreak.Model.figures;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.*;


public class Tile extends Shape {
    private char type ;
    private final Point center ;
    private final Point realCenter;
    private Color color ;
    private final double size ;
    private final Rectangle tile ;
    private int centerRadius ;

    public Tile(Point c, char tileType, double size){
        super(c) ;
        this.type = tileType ;
        this.center = c ;
        this.size = size ;
        this.tile = new Rectangle() ;
        tile.setX(center.getX());
        tile.setY(center.getY());
        tile.setWidth(size);
        tile.setHeight(size);
        realCenter = new Point(center.getX() + size/2, center.getY() + size/2);
        switch(tileType){
            case '0' :
                this.color = MEDIUMPURPLE ;
                this.centerRadius = 2 ;
                tile.setOpacity(0.7);
                break ;
            case '1' :
                this.color = Color.SLATEGREY;
                this.centerRadius = 1 ;
                tile.setStroke(BLACK);
                break ;
            case '2' :
                this.color = CYAN  ;
                tile.setOpacity(0.5);
                this.centerRadius = 2 ;
                break ;
            case '3' :
                this.color = BLACK  ;
                this.centerRadius = 2 ;
                break ;
        }
        tile.setFill(color);

    }

    @Override
    public void setOrigin(Point point) {

    }
    public void setColor(Color color){tile.setFill(color);}

    @Override
    public boolean isOn(Point point) {
        return false;
    }

    @Override
    public void update() {}
    public Node getShape(){return tile; }
    public Point getRealCenter(){return this.realCenter ;}
    public Node getCenterCircle(){
        Circle realC = new Circle(realCenter, BLACK, centerRadius);
        return realC.getShape() ;
    }
    public static Color getColorFromType(String type){
        Color col = null ;
        switch (type){
            case "classical" : col = DARKBLUE ; break ;
            case "slow" : col = RED ; break ;
            case "fast" : col = Color.ORANGE ; break ;
            case "sniper" : col = BLACK ; break ;
            case "slowing" : col = DARKGOLDENROD; break;
            case "money" : col = INDIANRED; break;
        }
        return col ;
    }
    public int getSize(){return (int)size ; }
    public char getType(){return type ;}

    public void setType(char c){
        if(c == '2'){
            tile.setOpacity(1);
        }
        type = c ;
    }

}


