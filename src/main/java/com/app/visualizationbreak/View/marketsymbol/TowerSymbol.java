package com.app.visualizationbreak.View.marketsymbol;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//symbole de l'objet Tower
public class TowerSymbol {
    private final String type ;
    private final int size = 20 ;
    private final Rectangle rect ;


    public TowerSymbol(String type){
        rect = new Rectangle();
        rect.setWidth(size);
        rect.setHeight(20);
        this.type = type;
        switch (type){
            case "classical": rect.setFill(Color.DARKBLUE); break ;
            case "slow": rect.setFill(Color.RED); break ;
            case "fast": rect.setFill(Color.ORANGE); break ;
            case "sniper": rect.setFill(Color.BLACK); break ;
            case "slowing": rect.setFill(Color.DARKGOLDENROD); break;
        }
    }

    public Node getShape(){return rect ;}

    public String getType() {
        return type;
    }

}
