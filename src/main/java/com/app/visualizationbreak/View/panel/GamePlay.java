package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Controller.objecthandler.ObjectModeHandler;
import com.app.visualizationbreak.Controller.objecthandler.TransparentFarmerHandler;
import com.app.visualizationbreak.Controller.objecthandler.TransparentTowerHandler;
import com.app.visualizationbreak.Model.figures.Drawing;
import com.app.visualizationbreak.Model.figures.Tile;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.ingameobjects.Farmer;
import com.app.visualizationbreak.Model.ingameobjects.Tower;
import com.app.visualizationbreak.utilities.PaneMeth;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class GamePlay extends BorderPane {
    private static GamePlay instance = null;
    private Drawing drawing ;
    private Market marketBuyable;
    private final PlayerInterface playerInterface;
    private UpgradeInterface upgradeInterface;
    private boolean answer = false;

    private GamePlay() throws IOException {
        super();
        PaneMeth.setImageMenu(this);
        playerInterface = new PlayerInterface(this);
        this.setTop(playerInterface);
        for(Node node : getChildren()){
            if(! (node instanceof Button)){
                node.setStyle("-fx-text-fill: white;");
            }
        }
    }

    public static GamePlay getInstance() throws IOException {
        if (GamePlay.instance == null) {
            GamePlay.instance = new GamePlay();
        }
        return GamePlay.instance;
    }

    public static void setInstance(GamePlay ninstance){
        GamePlay.instance = ninstance;
    }

    public void displayUserControl() {
        marketBuyable = new Market(this);
        this.setLeft(marketBuyable);
        upgradeInterface = new UpgradeInterface(this);
        this.setBottom(upgradeInterface);
    }

    public void displayTowerUpgradeInterface(Tile tile, Tower tower) {
        upgradeInterface.setUpTowerUpgradeInterface(tile, tower);
    }

    public void displayFarmerUpgradeInterface(Tile tile, Farmer farmer) {
        upgradeInterface.setUpFarmerUpgradeInterface(tile,farmer);
    }

    public void removeUpgradeInterface() {
        upgradeInterface.removeUpgradeInterface();
    }

    //methode qui permet de gérer les tours sur le drawing
    public void enterTowerMode(String type) {
        drawing.enterTowerMode(type);
        marketBuyable.enterTowerMode(type);
        drawing.setOnMouseEntered(new TransparentTowerHandler(this, type));
    }

    //permet de sortir du TowerMode
    public void exitTowerMode() {
        drawing.exitTowerMode();
        marketBuyable.exitTowerMode();
    }

    //permet de gérer les farmers
    public void enterFarmerMode(String type) throws IOException {
        drawing.setOnMouseEntered(new TransparentFarmerHandler(type));
        marketBuyable.enterFarmerMode(type);
    }

    public void exitFarmerMode () {
        drawing.exitFarmerMode();
        marketBuyable.exitFarmerMode();
    }
    public void exitAllModes(){exitFarmerMode();exitTowerMode();}
    public void setTransTower(Rectangle transparentTower) { marketBuyable.setTransTower(transparentTower); }

    public Market getMarketBuyable(){return marketBuyable ;}

    public void setTransFarmer(Rectangle transparentFarmer) { marketBuyable.setTransFarmer(transparentFarmer);}

    public void setTowerRange(Circle towerRange) { marketBuyable.setTowerRange(towerRange); }

    public Drawing getDrawing() {return drawing;}

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    public boolean isInterfaceDisplayed() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer= answer;
    }

    public void updateDrawing(){
        drawing = new Drawing(Party.getInstance().getCurrentLevel());
        drawing.setOnMouseClicked(new ObjectModeHandler());
        Party.getInstance().setLevelState(true);
        Party.getInstance().setWavesState(false);
        drawing.drawQuadrillage(Party.getInstance().getTilesSize());
        drawing.setPrefWidth(1000);
        setDrawing(drawing);
        setCenter(drawing);
        displayUserControl();
    }

    public PlayerInterface getPlayerInterface(){return playerInterface;}
}