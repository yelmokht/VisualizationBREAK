package com.app.visualizationbreak.Model.factories;

import com.app.visualizationbreak.Model.figures.Point;
import com.app.visualizationbreak.Model.gameorganisator.Level;
import com.app.visualizationbreak.Model.ingameobjects.Farmer;
import javafx.scene.text.Text;

public class FarmerFactory {

    public static Farmer createFarmer (String type, Point point, Level level) throws Exception{
        Farmer farmer = null;
        if(point.getY()<0 | point.getX() < 0){
            throw new Exception("Point out of map.");
        }
        else{
            switch (type) {
                case "money" :
                    farmer = new Farmer(point,0.1, 2, 100, 75, 50, level);
                    break;
            }
            return farmer;
        }

    }

    public static Text getDescription(String type) {
        Text descriptionFarmer = new Text();
        switch (type) {
            case "money":
                descriptionFarmer.setText("Production: 5\nTaux de production: 0.1\nPrix: 100\nUpgrade: 75\nDétruire: +50");
                break;
        }
        return descriptionFarmer;
    }

}
