package com.app.visualizationbreak.View.marketsymbol;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//symbol de l'objet Farmer
public class FarmerSymbol {
    private final String type ;
    private final int size = 20 ;
    private final Rectangle rect ;

    public FarmerSymbol(String type){
        rect = new Rectangle();
        rect.setWidth(size);
        rect.setHeight(20);
        this.type = type;
        switch (type){
            case "money": rect.setFill(Color.INDIANRED); break ;
        }
    }

    public Node getShape(){return rect ;}

    public String getType() { return type; }
}

