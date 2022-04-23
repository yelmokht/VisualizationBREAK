package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Controller.objecthandler.FarmerHandler;
import com.app.visualizationbreak.Controller.objecthandler.TowerHandler;
import com.app.visualizationbreak.Model.factories.FarmerFactory;
import com.app.visualizationbreak.Model.factories.TowerFactory;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.View.marketsymbol.FarmerSymbol;
import com.app.visualizationbreak.View.marketsymbol.TowerSymbol;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Market extends ScrollPane {
    private final GamePlay gamePlay;
    private GridPane marketBuyable;
    private TowerSymbol classicalTowerSymbol;
    private TowerSymbol slowTowerSymbol;
    private TowerSymbol fastTowerSymbol;
    private TowerSymbol sniperTowerSymbol;
    private TowerSymbol slowingTowerSymbol;
    private FarmerSymbol moneyFarmerSymbol;
    private Rectangle transparentTower;
    private Rectangle transparentFarmer;
    private Circle towerRange;
    private boolean towerMode = false ;
    private boolean farmerMode = false;
    private Label cursor ;

    public Market(GamePlay gamePlay) {
        super();
        this.gamePlay = gamePlay;
        initMarketBuyable();
        setUpMarketBuyable();
    }


    private void initMarketBuyable() {
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setPadding(new Insets(10));
        this.marketBuyable = new GridPane();
        this.marketBuyable.setHgap(10);
        this.marketBuyable.setVgap(10);
        this.setContent(marketBuyable);
    }

    private void setUpMarketBuyable() {
        Label title = new Label("Market");
        Label section1 = new Label("Towers");
        Label subsection11 = new Label("Classical Tower");
        Label subsection12 = new Label("Slow Tower");
        Label subsection13 = new Label("Fast Tower");
        Label subsection14 = new Label("Sniper Tower");
        Label subsection15 = new Label("Slowing Tower");
        Label section2 = new Label("Farmers");
        Label subsection21 = new Label("Money Farmer");


        classicalTowerSymbol = new TowerSymbol("classical");
        slowTowerSymbol = new TowerSymbol("slow");
        fastTowerSymbol = new TowerSymbol("fast");
        sniperTowerSymbol = new TowerSymbol("sniper");
        slowingTowerSymbol = new TowerSymbol("slowing");

        moneyFarmerSymbol = new FarmerSymbol("money");


        Text txt11 = TowerFactory.getDescription(classicalTowerSymbol.getType());
        txt11.setTextAlignment(TextAlignment.LEFT);

        Text txt12 = TowerFactory.getDescription(slowTowerSymbol.getType());
        txt12.setTextAlignment(TextAlignment.LEFT);

        Text txt13 = TowerFactory.getDescription(fastTowerSymbol.getType());
        txt13.setTextAlignment(TextAlignment.LEFT);

        Text txt14 = TowerFactory.getDescription(sniperTowerSymbol.getType());
        txt14.setTextAlignment(TextAlignment.LEFT);

        Text txt15 = TowerFactory.getDescription(slowingTowerSymbol.getType());
        txt15.setTextAlignment(TextAlignment.LEFT);

        Text txt21 = FarmerFactory.getDescription(moneyFarmerSymbol.getType());

        this.marketBuyable.setGridLinesVisible(false);

        this.marketBuyable.add(title, 0, 0, 2, 1);
        GridPane.setHalignment(title, HPos.CENTER);

        marketBuyable.add(section1, 0, 1);

        marketBuyable.add(subsection11, 0, 2);
        marketBuyable.add(classicalTowerSymbol.getShape(), 0, 3);
        marketBuyable.add(txt11, 1, 3);

        marketBuyable.add(subsection12, 0, 4);
        marketBuyable.add(slowTowerSymbol.getShape(), 0, 5);
        marketBuyable.add(txt12, 1, 5);

        marketBuyable.add(subsection13, 0, 6);
        marketBuyable.add(fastTowerSymbol.getShape(), 0, 7);
        marketBuyable.add(txt13, 1, 7);

        marketBuyable.add(subsection14, 0, 8);
        marketBuyable.add(sniperTowerSymbol.getShape(), 0, 9);
        marketBuyable.add(txt14, 1, 9);

        marketBuyable.add(subsection15, 0, 10);
        marketBuyable.add(slowingTowerSymbol.getShape(), 0, 11);
        marketBuyable.add(txt15, 1, 11);

        marketBuyable.add(section2, 0, 12);

        marketBuyable.add(subsection21, 0, 13);
        marketBuyable.add(moneyFarmerSymbol.getShape(), 0, 14);
        marketBuyable.add(txt21, 1, 14);

        setIconInteraction();
    }

    public void setIconInteraction() {
        classicalTowerSymbol.getShape().setOnMouseClicked(new TowerHandler("classical"));
        slowTowerSymbol.getShape().setOnMouseClicked(new TowerHandler("slow"));
        fastTowerSymbol.getShape().setOnMouseClicked(new TowerHandler("fast"));
        sniperTowerSymbol.getShape().setOnMouseClicked(new TowerHandler("sniper"));
        slowingTowerSymbol.getShape().setOnMouseClicked(new TowerHandler("slowing"));
        moneyFarmerSymbol.getShape().setOnMouseClicked(new FarmerHandler("money"));
    }

    public void enterTowerMode(String type) {
        cursor = new Label();
        gamePlay.getDrawing().enterTowerMode(type);
        gamePlay.getDrawing().setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                transparentTower.setX(event.getX() - Party.getInstance().getTilesSize() / 2);
                transparentTower.setY(event.getY() - Party.getInstance().getTilesSize() / 2);
                towerRange.setCenterX(event.getX());
                towerRange.setCenterY(event.getY());
            }
        });
        gamePlay.getDrawing().setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gamePlay.getDrawing().getChildren().removeAll(transparentTower, towerRange);
            }
        });
    }

    public void exitTowerMode() {
        gamePlay.getDrawing().setOnMouseEntered(null);
        gamePlay.getDrawing().setOnMouseMoved(null);
        if (transparentTower != null) {
            gamePlay.getDrawing().getChildren().removeAll(transparentTower, towerRange);
            towerMode = false;
            transparentTower = null;
            towerRange = null;
        }
    }

    public void enterFarmerMode(String type) {
        cursor = new Label();
        gamePlay.getDrawing().enterFarmerMode(type);
        gamePlay.getDrawing().setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                transparentFarmer.setX(event.getX() - Party.getInstance().getTilesSize() / 2);
                transparentFarmer.setY(event.getY() - Party.getInstance().getTilesSize() / 2);
            }
        });
        gamePlay.getDrawing().setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gamePlay.getDrawing().getChildren().remove(transparentFarmer);
            }
        });
    }

    public void exitFarmerMode() {
        gamePlay.getDrawing().setOnMouseEntered(null);
        if (transparentFarmer != null) {
            gamePlay.getDrawing().getChildren().remove(transparentFarmer);
            farmerMode = false;
            transparentFarmer = null;
        }
    }

    // Attribut du mode tour
    // acceder au mode tour
    public void setTowerMode(boolean state){towerMode = state ;}
    public boolean getTowerMode(){return towerMode ;}
    public void setTransTower(Rectangle transparentTower) {this.transparentTower = transparentTower;}
    public void setTransFarmer(Rectangle transparentFarmer) {this.transparentFarmer = transparentFarmer;}
    public Rectangle getTransTower() {return transparentTower;}
    public Rectangle getTransFarmer() { return transparentFarmer;}
    public void setTowerRange(Circle towerRange) {this.towerRange = towerRange;}
    public Circle getTowerRange(){return towerRange;}
}
