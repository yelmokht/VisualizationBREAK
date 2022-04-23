package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Controller.freemode.BackFreeModeListener;
import com.app.visualizationbreak.Controller.freemode.ConfirmFreeModeListener;
import com.app.visualizationbreak.Controller.freemode.FMFirstTileHandler;
import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.Model.figures.Tile;
import com.app.visualizationbreak.Model.gameorganisator.MapFile;
import com.app.visualizationbreak.Model.gameorganisator.MapFileBuilder;
import com.app.visualizationbreak.View.scenes.FreeModeScene;
import com.app.visualizationbreak.View.scenes.GameScene;
import com.app.visualizationbreak.utilities.PaneMeth;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.ArrayList;

public class FreeModeDrawing extends BorderPane {
    private static FreeModeDrawing instance  = null ;
    private final Pane drawing ;
    private ArrayList<ArrayList<Rectangle>> rectList ;
    private ArrayList<Tile> tilesList;
    private int firstTileLoc ;
    private final int tilesSize = (int) (Screen.getPrimary().getBounds().getMaxY()/36);
    private final int mapWidth = 32 ;
    private final Color voidColor = Color.MEDIUMPURPLE;
    private final Color pathColor = Color.SLATEGREY;
    private boolean firstTileSelected = false;
    private final FreeModeScene scene ;
    private Label tutoDraw ;

    // mode "Free Drawing"
    public FreeModeDrawing(FreeModeScene scene) throws IOException {
        PaneMeth.setImageMenu(this);
        this.scene = scene ;
        drawing = new Pane() ;
        rectList = new ArrayList<>();
        drawVoidTiles() ;
        drawQuadrillage();
        setCenter(drawing);
        tutoDraw = Tutorial.getInstance("draw").getLabel();
        tutoDraw.setBackground(new Background(new BackgroundImage(new Image("file:src/main/java/com/app/visualizationbreak/View/images/text-background.jpg",
                800, 300, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));
        tutoDraw.setAlignment(Pos.TOP_CENTER);
        tutoDraw.setTextAlignment(TextAlignment.CENTER);
        setRight(tutoDraw);
    }

    // mode "Load Map"
    public FreeModeDrawing(FreeModeScene scene, int mapNumber) throws IOException {
        this.scene = scene ;
        drawing = new Pane() ;
        tilesList = new ArrayList<>();
        tilesList = (new MapFile("src\\main\\java\\com\\app\\visualizationbreakbreak\\Model\\Maps\\map"+mapNumber+".txt")).makeMapTiles(tilesSize);
        for(Tile tile : tilesList){drawing.getChildren().add(tile.getShape());}
        Button confirmButton = new Button("Confirm !") ;
        confirmButton.setOnAction(new ConfirmFreeModeListener(mapNumber));

        Button backButton = new Button("Back");
        backButton.setOnAction(new BackFreeModeListener());
        drawQuadrillage();
        TilePane tP = new TilePane();
        tP.getChildren().addAll(confirmButton, backButton);
        setTop(tP);
        setCenter(drawing);
    }

    public void drawQuadrillage(){
        for (int i = 0; i <=32*tilesSize; i+=tilesSize) {
            Line line1 = new Line(i, 0, i, 32*tilesSize);
            line1.setStroke(Color.LIGHTGRAY);
            Line line2 = new Line(0, i, 32*tilesSize, i);
            line2.setStroke(Color.LIGHTGRAY);
            drawing.getChildren().addAll(line1, line2);
        }
    }

    public void drawVoidTiles(){
        for(int i = 0 ; i< mapWidth; i++){
            ArrayList<Rectangle> lineList = new ArrayList<>() ;
            for(int j = 0 ; j < mapWidth ; j ++){
                Rectangle r = new Rectangle() ;
                r.setHeight(tilesSize);
                r.setWidth(tilesSize);
                r.setFill(voidColor);
                r.setX(j*tilesSize);
                r.setY(i*tilesSize);
                r.setOpacity(1);
                drawing.getChildren().add(r) ;
                lineList.add(r) ;
                if(j == 0 && !firstTileSelected){
                    r.setOnMouseClicked(new FMFirstTileHandler(this, r));
                }
            }
            rectList.add(lineList) ;
        }
    }

    public static FreeModeDrawing getInstance(FreeModeScene scene) throws IOException {
        if(instance == null){
            instance = new FreeModeDrawing(scene) ;
        }
        return instance ;
    }

    public static FreeModeDrawing getInstance(FreeModeScene scene, int mapNumber) throws IOException {
        if(instance == null){
            instance = new FreeModeDrawing(scene, mapNumber) ;
        }
        return instance ;
    }

    public static FreeModeDrawing getInstance(){
        return instance ;
    }



    public int getFirstTileLoc(){return firstTileLoc ;}
    public void setFirstTileLoc(int l){firstTileLoc = l ;}
    public boolean getFirstTileSelected(){return firstTileSelected ;}
    public void setFirstTileSelected(boolean x){firstTileSelected = x ;}
    public int getTilesSize(){return tilesSize ;}
    public ArrayList<ArrayList<Rectangle>> getTileList(){return rectList ;}
    public int getMapWidth(){return mapWidth;}
    public Pane getDrawing(){return drawing ; }
    public FreeModeScene getFMScene(){return  scene ;}

    public void startGame(String sequence, int initRowNum) throws IOException {

        MapFileBuilder newMap = new MapFileBuilder(mapWidth);
        newMap.drawPath(sequence, initRowNum);
        newMap.writeMapInFile();
        try {
            GameLauncher.getStage().setScene(GameScene.getInstance("free"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
