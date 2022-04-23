package com.app.visualizationbreak.Model.figures;

import com.app.visualizationbreak.Model.gameorganisator.Level;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.ingameobjects.Farmer;
import com.app.visualizationbreak.Model.ingameobjects.Tower;
import com.app.visualizationbreak.Model.player.Player;
import com.app.visualizationbreak.View.panel.GamePlay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Drawing extends Pane implements Iterable<Shape> {
    private final ArrayList<Tile> tiles;         // Pour le moment : dessin du chemin uniquement
    private final ArrayList<Circle> pathCircles;
    private ArrayList<Shape> shapes;       // Les PNJ sont les seuls "shape"
    private final ArrayList<PNJ> pnjs ;
    private final ArrayList<Tower> towers ;
    private final Level level ;
    private int tilesWidth;
    private final Party currentParty ;
    private final Player currentPlayer ;
    private boolean firstTile = true ;


    public Drawing(Level level) {
        super();
        this.tiles = new ArrayList<>() ;
        this.shapes = new ArrayList<>();
        this.pnjs = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.level = level ;
        this.currentParty = level.getParty() ;
        this.currentPlayer = currentParty.getPlayer();
        this.level.setDrawing(this);
        drawMap();
        this.pathCircles = new ArrayList<>();
        this.setStyle("-fx-border-color: black;");
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(2), new EventHandler<ActionEvent>() {
            // refresh la position des PNJ et les retire si ils ont atteint la fin de leur chemin
            // supprime les projectile quand ils atteignent leur cible
            @Override
            public void handle(ActionEvent event) {
                int i = 0;
                while (i < shapes.size()) {
                    Shape s = shapes.get(i);
                    s.update();
                    if (s instanceof PNJ) {
                        if ( ((PNJ) s).getCenter().getX() >=Party.getInstance().getTilesSize()*32) {
                            remove(s);
                        }
                    }
                    else if(s instanceof Projectile){
                        if (((Projectile)s).hasTouched()){
                            remove(s);
                        }
                    }
                    i++;
                }
            }

        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public void drawMap(){
        ArrayList<Tile> mapTiles = level.getMapTiles() ;
        for(Tile tile : mapTiles){
            if (firstTile) {
                tilesWidth = tile.getSize() ;
                firstTile = false ;
            }
            addSquareTile(tile);
        }
    }

    public ArrayList<Tile> getTiles(){return this.tiles ;}

    public void drawQuadrillage(int tilesSize) {

        for (int i = 0; i <=32*tilesSize; i+=tilesSize) {
            Line line1 = new Line(i, 0, i, 32*tilesSize);
            line1.setStroke(Color.LIGHTGRAY);
            Line line2 = new Line(0, i, 32*tilesSize, i);
            line2.setStroke(Color.LIGHTGRAY);
            this.getChildren().addAll(line1, line2);
        }

    }

    public void addMovingPNJ(PNJ pnj) {
        shapes.add(pnj);
        getChildren().add(pnj.getShape()) ;
    }
    public void addSquareTile(Tile tile){
        tiles.add(tile);
        shapes.add(tile) ;
        Node tileShape = tile.getShape();
        this.getChildren().addAll(tileShape);
    }

    public void addProjectile(Projectile projectile) {
        shapes.add(projectile);
        getChildren().add(projectile.getShape());
    }

    public void addTower(Tower tower) {
        shapes.add(tower);
        this.getChildren().add(tower.getShape());
    }

    public void addFarmer(Farmer farmer) {
        shapes.add(farmer);
        this.getChildren().add(farmer.getShape());
    }

    public void setUpTileTower(Tile tile, Tower tower) {
        Tooltip towerInfo = new Tooltip("Tower");
        Tooltip.install(tile.getShape(),towerInfo);
        tile.getShape().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    try {
                        if (!GamePlay.getInstance().isInterfaceDisplayed()) {
                            GamePlay.getInstance().displayTowerUpgradeInterface(tile, tower);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    try {
                        if (GamePlay.getInstance().isInterfaceDisplayed()) {
                            GamePlay.getInstance().removeUpgradeInterface();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }});
    }

    public void setUpTileFarmer(Tile tile, Farmer farmer) {
        Tooltip towerInfo = new Tooltip("Farmer");
        Tooltip.install(tile.getShape(), towerInfo);
        tile.getShape().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    try {
                        if (!GamePlay.getInstance().isInterfaceDisplayed()) {
                            GamePlay.getInstance().displayFarmerUpgradeInterface(tile, farmer);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    try {
                        if (GamePlay.getInstance().isInterfaceDisplayed()) {
                            GamePlay.getInstance().removeUpgradeInterface();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        }});
    }



    public void clear() {
        this.shapes = new ArrayList<>() ;
        this.getChildren().clear();
    }

    private void remove(Shape s) {
        getChildren().remove(s.getShape());
        shapes.remove(s) ;
    }
    public void removeTower(Tile tile,Tower tower) throws IOException {
        this.getChildren().removeAll(tower.getShape());
        tile.setType('0');
        tile.setColor(Color.MEDIUMPURPLE);
        tile.getShape().setOpacity(0.7);
        GamePlay.getInstance().removeUpgradeInterface();
        tile.getShape().setOnMousePressed(null);
    }

    public void removeFarmer(Tile tile, Farmer farmer) throws IOException {
        this.getChildren().removeAll(farmer.getShape());
        tile.setType('0');
        tile.setColor(Color.MEDIUMPURPLE);
        tile.getShape().setOpacity(0.7);
        GamePlay.getInstance().removeUpgradeInterface();
        tile.getShape().setOnMousePressed(null);
    }

    @Override
    public Iterator<Shape> iterator() {
        return shapes.iterator();
    }

    // Tower mode : on peut interagir avec chaque case. Si c'est une case chemin alors en cliquant elle est remplacée
    // par une case Tower
    public void enterTowerMode(String type) {
        for(Tile tile : tiles){
            tile.getShape().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getButton() == MouseButton.PRIMARY){
                        if (tile.getType() == '0') {

                            /** creation d'une tour partie 1**/
                            Tower temporaryTower = null;
                            try {
                                temporaryTower = level.createTower(type, tile.getOrigin());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if(currentPlayer.getResources().getMoney()>=temporaryTower.getPrice()){
                                tile.setColor(Tile.getColorFromType(type));
                                tile.getShape().setOpacity(1);
                                currentPlayer.getResources().setMoney(currentPlayer.getResources().getMoney() - temporaryTower.getPrice());
                                temporaryTower.addAndStart();
                                setUpTileTower(tile,temporaryTower);
                                tile.setType('3');
                            }
                        }
                    }
                }
            });
        }
    }

    public void exitTowerMode(){
        for(Tile tile : tiles){
            tile.getShape().setOnMouseClicked(null);
        }
    }

    public void enterFarmerMode(String type) {
        for(Tile tile : tiles){
            tile.getShape().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getButton() == MouseButton.PRIMARY){
                        if (tile.getType() == '0') {
                            Farmer temporaryFarmer = null;
                            try {
                                temporaryFarmer = level.createFarmer(type, tile.getOrigin());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if(currentPlayer.getResources().getMoney()>=temporaryFarmer.getPrice()){
                                tile.setColor(Tile.getColorFromType(type));
                                tile.getShape().setOpacity(1);
                                currentPlayer.getResources().setMoney(currentPlayer.getResources().getMoney() - temporaryFarmer.getPrice());
                                level.addFarmer(temporaryFarmer);
                                temporaryFarmer.start();
                                setUpTileFarmer(tile,temporaryFarmer);
                                tile.setType('2');

                            }
                        }
                    }
                }
            });
        }
    }
    public void exitFarmerMode(){
        for(Tile tile : tiles){
            tile.getShape().setOnMouseClicked(null);
        }
    }

    public void drawTowerRange(javafx.scene.shape.Circle towerRange) {
        getChildren().add(towerRange);
    }
    public void clearTowerRange(javafx.scene.shape.Circle towerRange) {
        getChildren().remove(towerRange);
    }
    public void add(Rectangle transparentTower, javafx.scene.shape.Circle towerRange) { getChildren().addAll(transparentTower, towerRange);
    }
}
