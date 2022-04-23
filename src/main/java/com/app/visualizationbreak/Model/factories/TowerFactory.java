package com.app.visualizationbreak.Model.factories;

import com.app.visualizationbreak.Model.figures.Point;
import com.app.visualizationbreak.Model.gameorganisator.Level;
import com.app.visualizationbreak.Model.ingameobjects.ClassicalTower;
import com.app.visualizationbreak.Model.ingameobjects.SlowingTower;
import com.app.visualizationbreak.Model.ingameobjects.Tower;
import javafx.scene.text.Text;

public class TowerFactory {

    public static Tower getInstance(String type, Point point, Level level) throws Exception {
        Tower tower = null;
        if(point.getY()<0 | point.getX() < 0){
            throw new Exception("Point out of map.");
        }
        else{
            switch (type) {
                case "classical":
                    tower = new ClassicalTower(point, 1, 5, 120, 20, 50, 10, level);
                    break;
                case "slow":
                    tower = new ClassicalTower(point, 0.5, 10, 70, 30, 50, 15, level);
                    break;
                case "fast":
                    tower = new ClassicalTower(point, 2, 4, 70, 30, 50, 15, level);
                    break;
                case "sniper":
                    tower = new ClassicalTower(point, 0.4, 20, 250, 40, 50, 20, level);
                    break;
                case "slowing":
                    tower = new SlowingTower(point, 0.4, 0.2, 200, 50, 60, 15, level);
                    break;
            }
        }

        return tower;
    }

    public static Text getDescription(String type) {
        Text descriptionTower = new Text();
        switch (type) {
            case "classical":
                descriptionTower.setText("Dégâts: 5\nPortée de tir: 120\nVitesse de tir: 1\nPrix: 20\nUpgrade: 50\nDétruire: +10");
                break;
            case "slow":
                descriptionTower.setText("Dégâts: 10\nPortée de tir: 70\nVitesse de tir: 0.5\nPrix: 30\nUpgrade: 50\nDétruire: +15");
                break;
            case "fast":
                descriptionTower.setText("Dégâts: 4\nPortée de tir: 70\nVitesse de tir: 2\nPrix: 30\nUpgrade: 50\nDétruire: +15");
                break;
            case "sniper":
                descriptionTower.setText("Dégâts: 20\nPortée de tir: 250\nVitesse de tir: 0.4\nPrix: 40\nUpgrade: 50\nDétruire: +20");
                break;
            case "slowing":
                descriptionTower.setText("Vitesse en moins: 0.2\nPortée de tir: 200\nVitesse de tir: 0.4\nPrix: 50\nUpgrade :60\nDétruire: +15");
                break;
        }
        return descriptionTower;
    }
}

