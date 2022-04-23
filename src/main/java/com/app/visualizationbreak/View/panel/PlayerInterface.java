package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Controller.mapbuttons.LevelStateHandler;
import com.app.visualizationbreak.Controller.mapbuttons.NewLevelListener;
import com.app.visualizationbreak.Controller.mapbuttons.StartLevelListener;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.player.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class PlayerInterface extends TilePane {
    private final GamePlay gamePlay;
    private Label levelLabel ;
    private Label waveLabel ;
    private Label currentLevelLabel ;
    private Label currentWaveLabel ;
    private Label life;       //label qui va indiquer la vie du joueur
    private Label money;      //label qui va indiquer l'argent du joueur
    private Label cursor;
    private Label score;      //label qui va indiquer le score du joueur
    private Button nextLevel ;
    private Button startLevel ;
    private Button quitMenu ;

    public PlayerInterface (GamePlay gamePlay) {
        super();
        this.gamePlay = gamePlay;
        initPlayerInterface();
        setUpPlayerInterface();
        for(Node node : getChildren()){
            if(node instanceof Label)
            node.setStyle("-fx-text-fill: white;");
        }
    }

    private void initPlayerInterface() {
        this.setStyle("-fx-border-color: black;");
        this.setHgap(5);
        this.setVgap(10);
        this.setPadding(new Insets(10,10,10,10));
    }

    private void setUpPlayerInterface() {
        Canvas canvas = new Canvas(40, 40);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.FUCHSIA);
        gc.setLineWidth(3.0);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.RED);
        gc.strokeRect(5, 5, 30, 30);
        gc.rotate(45);

        this.getChildren().add(canvas);

        Label playerName = new Label(Player.getInstance().getName());
        this.getChildren().add(playerName);

        // boutons de vitesse et de pause
        Button button1 = new Button("1x");
        button1.setAlignment(Pos.BOTTOM_RIGHT);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Party.getInstance().getLevelState()) {
                    Party.getInstance().setGameSpeed(1);
                    gamePlay.getDrawing().setDisable(false); //le drawing redevient actif
                    gamePlay.getDrawing().setOpacity(1);
                }
            }
        });

        Button button2 = new Button("2x");
        button2.setAlignment(Pos.BOTTOM_RIGHT);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Party.getInstance().getLevelState()) {
                    Party.getInstance().setGameSpeed(2);
                    gamePlay.getDrawing().setDisable(false); //le drawing redevient actif
                    gamePlay.getDrawing().setOpacity(1);
                }
            }
        });

        Button button3 = new Button("4x");
        button3.setAlignment(Pos.BOTTOM_RIGHT);
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Party.getInstance().getLevelState()) {
                    Party.getInstance().setGameSpeed(4);
                    gamePlay.getDrawing().setDisable(false); //le drawing redevient actif
                    gamePlay.getDrawing().setOpacity(1);
                }
            }
        });

        Button button4 = new Button("Pause");
        button4.setAlignment(Pos.BOTTOM_RIGHT);
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Party.getInstance().getLevelState()) {
                    Party.getInstance().setGameSpeed(0);
                    gamePlay.getDrawing().setDisable(true); //le drawing n'est plus 'actif"
                    gamePlay.getDrawing().setOpacity(0.2);
                }
            }
        });

        /*Button button5 = new Button("Home") ;
        button5.setAlignment(Pos.BOTTOM_RIGHT);
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameLauncher.getScene().setRoot(TitleScreen.getInstance());
                Party.resetInstance();
                for(Node node : getChildren()){
                    node = null;
                }
            }
        });*/


        levelLabel = new Label();
        waveLabel = new Label();
        currentLevelLabel = new Label("Level : ");
        currentWaveLabel = new Label("Wave : ");
        life = new Label();
        money = new Label();
        cursor = new Label();
        score = new Label();

        nextLevel =  new Button("Next level") ;
        startLevel = new Button("Ready !") ;

        this.getChildren().addAll(button1, button2, button3, button4);
        this.getChildren().addAll(nextLevel, startLevel, levelLabel, currentLevelLabel, waveLabel, currentWaveLabel, life, money, score);
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(50), new LevelStateHandler(gamePlay, nextLevel, startLevel, currentLevelLabel, currentWaveLabel, life, money, score)));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public void disableNewLevel(){
        nextLevel.setOpacity(0.2);
        nextLevel.setOnAction(null);
    }
    public void enableNewLevel(NewLevelListener newLevelListener){
        nextLevel.setOpacity(1);
        nextLevel.setOnAction(newLevelListener);
    }
    public void disableStartLevel(){
        startLevel.setOpacity(0.2);
        startLevel.setOnAction(null);
    }
    public void enableStartLevel(StartLevelListener startLevelListener){
        startLevel.setOpacity(1);
        startLevel.setOnAction(startLevelListener);
    }

}
