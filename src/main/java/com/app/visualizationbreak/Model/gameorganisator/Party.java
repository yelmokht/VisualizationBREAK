package com.app.visualizationbreak.Model.gameorganisator;

import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.Model.player.Player;
import com.app.visualizationbreak.Model.serialization.ScoreSaved;
import com.app.visualizationbreak.View.scenes.EndScene;
import javafx.application.Platform;
import javafx.stage.Screen;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static com.app.visualizationbreak.utilities.ListMeth.triBulle;


public class Party {
    private static Party instance = null ;
    private final Player player ;
    private static int gameSpeed ;
    private final int tilesSize ;
    private int maxLevelNumber, currentLevelNumber;
    private Level currentLevel ;
    private Wave currentWave ;
    private boolean levelsHasBegun ;     // quand on appuie sur "next level", ça charge la map
    private boolean wavesHasBegun ;      // quand on appuie sur "Ready!" les vagues commencent
    private boolean hasBegun, isPaused ;
    private List<String> top5 ;        //arraylist des differents score
    private String mode;

    // on construit une partie avec un mode
    private Party(String mode){
        this.player = Player.getInstance();
        gameSpeed = 1;
        this.mode = mode ;
        this.levelsHasBegun = false ;
        this.wavesHasBegun = false ;
        this.hasBegun = false ;
        this.isPaused = true ;
        this.tilesSize = (int) (Screen.getPrimary().getBounds().getMaxY()/36) ;
        switch (mode){
            case "regular":
                maxLevelNumber = 6 ;
                break ;
            case "test":
                maxLevelNumber = 10 ;
            default:
                maxLevelNumber = 666 ;

        }
        this.top5 = Collections.emptyList();
    }
    // pour les tests unitaires (fixer une taille de Tile sans avoir initié une fenêtre)
    private Party(String mode, boolean test){
        this.player = Player.getInstance();
        gameSpeed = 1;
        this.mode = mode ;
        this.levelsHasBegun = false ;
        this.wavesHasBegun = false ;
        this.hasBegun = false ;
        this.tilesSize = 32 ;
        switch (mode){
            case "regular":
                maxLevelNumber = 6 ;
                break ;
            case "test":

            default:
                maxLevelNumber = 666 ;

        }
        this.top5 = Collections.emptyList();
    }

    // pour la création de la partie il faut préciser le mode
    public static Party getInstance(String mode) {
        if (Party.instance == null){
            Party.instance = new Party(mode);
        }
        return Party.instance;
    }

    // pour les tests unitaires
    public static Party getInstance(String mode, boolean test){
        if (Party.instance == null){
            Party.instance = new Party(mode, test);
        }
        return Party.instance;
    }

    // pour accéder à l'objet Party sans préciser le mode : souvent utilisé partout dans le projet
    public static Party getInstance() {
        return Party.instance;
    }

    public static void resetInstance() {
        instance = null ;
    }

    public Level getCurrentLevel(){return currentLevel ;}
    public Wave getCurrentWave(){return currentWave ;}
    public void setCurrentLevel(Level level){currentLevel = level ;}
    public void setCurrentWave(Wave wave){currentWave = wave;}

    public int getTilesSize() {
        return tilesSize ;
    }
    public int getMaxLevelNumber(){return maxLevelNumber;}
    public Player getPlayer(){return player;}
    public void setLevelState(boolean state){
        levelsHasBegun = state ;
        Player.getInstance().setInLevel(state);
    }
    public void setWavesState(boolean state){
        wavesHasBegun = state ;
        Player.getInstance().setInWave(state);
    }
    public boolean getLevelState(){return levelsHasBegun ;}
    public boolean getWavesState(){return wavesHasBegun ;}
    public boolean getState(){return hasBegun ;}
    public void setState(boolean state){hasBegun = state ;}
    public List<String> getTop5(){
        return this.top5;
    }
    public void setMode(String mode){this.mode = mode;}

    public String getMode(){return mode;}


    public static int getGameSpeed(){return gameSpeed;}
    //fonction qui, par defaut, renvoie true qd le jeu n'est pas sur pause
    public boolean NotInPause(){
        boolean res = gameSpeed != 0;
        return res;
    }
    public void setGameSpeed(int speed) {
        if (speed >= 0) {
            gameSpeed = speed;
        }
    }

    //methode qui va finir la partie une fois la vie epuisee ou tous les niveaux finis
    public void finish() throws IOException{
        int score = Player.getInstance().getScore();
        String name = Player.getInstance().getName();
        ScoreSaved t = new ScoreSaved (score, name);
        String data = t.getName();
        data = data + ",";
        data = data + t.getScore();
        String filename= "src\\main\\java\\com\\app\\visualizationbreakbreak\\Model\\serialization\\"+mode+".txt" ;
        FileWriter file = new FileWriter(filename,true);  //le true permet de rajouter du texte
        file.append( data + "\n");         //rajoute le score et le nom du joueur
        file.close();

        try
        {
            top5 =
                    Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
            triBulle(top5);
        }

        catch (IOException e)
        {
            e.printStackTrace();}


        Platform.runLater( new Runnable() {
            @Override
            public void run() {
                try {
                    GameLauncher.getStage().setScene(EndScene.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }});
    }

    public void makeBegin() {
        hasBegun = true;
    }

    public void entersNextLevel(int currentLevelNumber) throws Exception {
        this.currentLevelNumber = currentLevelNumber+1 ;
        currentLevel = new Level(currentLevelNumber+1, this, mode) ;
        setLevelState(true);
        setWavesState(true);
    }

    public void finishedLevel() throws IOException {
        setLevelState(false);
        setWavesState(false);
        if(currentLevelNumber >= maxLevelNumber){
            finish();
        }
    }
}