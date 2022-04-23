package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Controller.RemoveListener;
import com.app.visualizationbreak.Controller.UpgradeListener;
import com.app.visualizationbreak.Model.figures.Tile;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.ingameobjects.ClassicalTower;
import com.app.visualizationbreak.Model.ingameobjects.Farmer;
import com.app.visualizationbreak.Model.ingameobjects.SlowingTower;
import com.app.visualizationbreak.Model.ingameobjects.Tower;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static javafx.scene.paint.Color.BLACK;

public class UpgradeInterface extends VBox {
    private final GamePlay gamePlay;
    private final Party party;
    private GridPane upgradeInterface;
    private Button upgrade, remove;
    private Circle towerRange;

    public UpgradeInterface(GamePlay gamePlay) {
        super();
        this.gamePlay = gamePlay;
        this.party = Party.getInstance();
        towerRange = gamePlay.getMarketBuyable().getTowerRange();
        setSpacing(10);
    }


    public void setUpTowerUpgradeInterface(Tile tile, Tower tower) {
        upgradeInterface = new GridPane();
        upgradeInterface.setPadding(new Insets(10));
        upgradeInterface.setHgap(10);
        upgradeInterface.setVgap(10);

        Label title = new Label("Stats");
        Label stat2 = new Label("Portée:");
        Label stat3 = new Label("Vitesse:");

        upgradeInterface.add(title, 0, 0, 2, 1);
        GridPane.setHalignment(title, HPos.CENTER);

        Label actionLabeltitle = new Label();
        Label actionLabel0 = new Label();
        Label actionLabel1 = new Label();
        Label rangeLabel0 = new Label();
        Label rangeLabel1 = new Label();
        Label rateLabel0 = new Label();
        Label rateLabel1 = new Label();
        final String[] myStat = new String[1];

        Label finalActionLabeltitle = actionLabeltitle;
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tower instanceof ClassicalTower) {
                    myStat[0] = Double.toString(((ClassicalTower) tower).getDPB());
                    finalActionLabeltitle.setText("Dégats:");
                    actionLabel0.setText(myStat[0]);
                } else if (tower instanceof SlowingTower) {
                    myStat[0] = Double.toString(((SlowingTower) tower).getSp());
                    finalActionLabeltitle.setText("Ralentiss.:");
                    actionLabel0.setText(myStat[0]);
                }

                rangeLabel0.setText(Double.toString(tower.getRange()));
                rateLabel0.setText(Double.toString(tower.getFireRate()));
                if (tower.getUpgradeLevel() == tower.getMaxUpLevel()) {
                    actionLabel1.setText("Max");
                    rangeLabel1.setText("Max");
                    rateLabel1.setText("Max");
                } else {
                    actionLabel1.setText(Double.toString(tower.getNextStat()));
                    rangeLabel1.setText(Double.toString(tower.getRange() + 15));
                    rateLabel1.setText(Double.toString(tower.getFireRate() + 0.5));
                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        upgradeInterface.add(actionLabeltitle, 0, 1);
        upgradeInterface.add(actionLabel0, 1, 1);
        upgradeInterface.add(new Label("->"), 2, 1);
        upgradeInterface.add(actionLabel1, 3, 1);

        upgradeInterface.add(stat2, 0, 2);
        upgradeInterface.add(rangeLabel0, 1, 2);
        upgradeInterface.add(new Label("->"), 2, 2);
        upgradeInterface.add(rangeLabel1, 3, 2);

        upgradeInterface.add(stat3, 0, 3);
        upgradeInterface.add(rateLabel0, 1, 3);
        upgradeInterface.add(new Label("->"), 2, 3);
        upgradeInterface.add(rateLabel1, 3, 3);

        towerRange = new Circle() ;
        towerRange.setRadius(tower.getRange());
        towerRange.setStroke(BLACK);
        towerRange.setFill(null);
        towerRange.setCenterX(tile.getRealCenter().getX());
        towerRange.setCenterY(tile.getRealCenter().getY());

        gamePlay.getDrawing().drawTowerRange(towerRange);

        upgrade = new Button("Upgrade");
        upgrade.setOnAction(new UpgradeListener(tower,towerRange));
        remove = new Button("Remove Tower");
        remove.setOnAction(new RemoveListener(tile, tower));
        gamePlay.setAnswer(true);

        this.getChildren().addAll(upgradeInterface, upgrade, remove);
        for(Node node : upgradeInterface.getChildren()){
            if(true){
                node.setStyle("-fx-text-fill: white;");
            }
        }
    }

    public void setUpFarmerUpgradeInterface(Tile tile, Farmer farmer) {
        upgradeInterface = new GridPane();
        upgradeInterface.setPadding(new Insets(10));
        upgradeInterface.setHgap(10);
        upgradeInterface.setVgap(10);

        Label title = new Label("Stats");
        Label stat1 = new Label("Production (argent):");
        Label stat2 = new Label("Taux de production:");

        Label productionLabel0 = new Label();
        Label productionLabel1 = new Label();
        Label productionRateLabel0 = new Label();
        Label productionRateLabel1 = new Label();


        Timeline timer = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                productionLabel0.setText(Integer.toString(farmer.getProduction()));
                productionRateLabel0.setText(Double.toString(farmer.getProductionRate())) ;
                if (farmer.getUpgradeLevel() == farmer.getMaxUpLevel()) {
                    productionLabel1.setText("Max");
                    productionRateLabel1.setText("Max"); }
                else{
                    productionLabel1.setText(Integer.toString(farmer.getProduction() + 1));
                    productionRateLabel1.setText(Double.toString(farmer.getNextProdRate()));
                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        upgradeInterface.add(title,0,0,2,1);
        GridPane.setHalignment(title, HPos.CENTER);

        if (farmer != null) {
            upgradeInterface.add(stat1, 0, 1);
            upgradeInterface.add(productionLabel0, 1, 1);
            upgradeInterface.add(new Label("->"), 2, 1);
            upgradeInterface.add(productionLabel1, 3, 1);

            upgradeInterface.add(stat2, 0, 2);
            upgradeInterface.add(productionRateLabel0, 1, 2);
            upgradeInterface.add(new Label("->"), 2, 2);
            upgradeInterface.add(productionRateLabel1, 3, 2);
        }

        upgrade = new Button("Upgrade");
        upgrade.setOnAction(new UpgradeListener(farmer));

        remove= new Button("Remove Farmer");
        remove.setOnAction(new RemoveListener(tile, farmer));
        gamePlay.setAnswer(true);
        this.getChildren().addAll(upgradeInterface, upgrade, remove);
        for(Node node : upgradeInterface.getChildren()){
            node.setStyle("-fx-text-fill: white;");
        }
    }

    public void removeUpgradeInterface() {
        getChildren().removeAll(upgradeInterface,upgrade, remove);
        gamePlay.getDrawing().clearTowerRange(towerRange);
        gamePlay.setAnswer(false);
        }
}
