package com.app.visualizationbreak.Controller;

import com.app.visualizationbreak.Model.figures.Tile;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.ingameobjects.Farmer;
import com.app.visualizationbreak.Model.ingameobjects.Tower;
import com.app.visualizationbreak.Model.player.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

public class RemoveListener implements EventHandler<ActionEvent> {
    private final Tile tile;
    private Tower tower;
    private Farmer farmer;
    private final String type;

    public RemoveListener(Tile tile, Tower tower) {
        this.tile = tile;
        this.tower = tower;
        this.type = "tower";
    }

    public RemoveListener(Tile tile, Farmer farmer) {
        this.tile = tile;
        this.farmer = farmer;
        this.type = "farmer";
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (type) {
            case "tower":
                try {
                    Party.getInstance().getCurrentLevel().removeTower(tile, tower);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Player.getInstance().getResources().setMoney(Player.getInstance().getResources().getMoney() + tower.getRemovePrice());

                break;

            case "farmer":
                try {
                    Party.getInstance().getCurrentLevel().removeFarmer(tile, farmer);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Player.getInstance().getResources().setMoney(Player.getInstance().getResources().getMoney() + farmer.getRemovePrice());
                break;
        }
    }
}
