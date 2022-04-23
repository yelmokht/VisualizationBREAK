package com.app.visualizationbreak.Controller;

import com.app.visualizationbreak.Model.ingameobjects.Farmer;
import com.app.visualizationbreak.Model.ingameobjects.Tower;
import com.app.visualizationbreak.Model.player.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;


public class UpgradeListener implements EventHandler<ActionEvent> {
    private Tower tower;
    private Circle towerRange;
    private Farmer farmer;
    private final String type;

    public UpgradeListener(Tower tower, Circle towerRange) {
        this.tower = tower;
        this.towerRange = towerRange;
        this.type = "tower";
    }

    public UpgradeListener(Farmer farmer) {
        this.farmer = farmer;
        this.type = "farmer";
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (type) {
            case "tower":
                if (Player.getInstance().getResources().getMoney() >= tower.getUpgradePrice() & tower.getUpgradeLevel() < tower.getMaxUpLevel()) {
                    Player.getInstance().getResources().setMoney(Player.getInstance().getResources().getMoney() - tower.getUpgradePrice());
                    tower.upgrade();
                    towerRange.setRadius(tower.getRange());
                } else {
                    System.out.println("Vous ne pouvez pas upgrade cette tower !");
                }
                break;

            case "farmer":
                if (Player.getInstance().getResources().getMoney() >= farmer.getUpgradePrice() & farmer.getUpgradeLevel() < farmer.getMaxUpLevel()) {
                    Player.getInstance().getResources().setMoney(Player.getInstance().getResources().getMoney() - farmer.getUpgradePrice());
                    farmer.upgrade();
                } else {
                    System.out.println("Vous ne pouvez pas upgrade ce farmer !");
                }
                break;
        }
    }
}