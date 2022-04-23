package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Controller.LevelSelectListener;
import com.app.visualizationbreak.Controller.freemode.FMLevelSelectListener;
import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.utilities.PaneMeth;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class LevelSelect extends BorderPane {
    //private Game game;
    public static LevelSelect instance ;
    private Button startlevel, freemode, leaderboard, back;
    private HBox hBoxLevels;


    public LevelSelect() {
        super();
        PaneMeth.setImageMenu(this);
        setHBoxLevels();
        this.setCenter(hBoxLevels);
        hBoxLevels.setAlignment(Pos.CENTER);
    }
    public static LevelSelect getInstance(){
        if(instance == null){
            instance = new LevelSelect();
        }
        return instance;
    }
    private void setHBoxLevels() {
        this.hBoxLevels = new HBox();
        this.hBoxLevels.setSpacing(20);

        this.startlevel = new Button("Regular Adventure");
        this.startlevel.setPrefSize(150, 20);
        startlevel.setOnAction(new LevelSelectListener());

        this.freemode = new Button("Freemode");
        this.freemode.setPrefSize(150, 20);
        this.freemode.setOnAction(new FMLevelSelectListener());

        this.leaderboard = new Button("Leaderboards");
        this.leaderboard.setPrefSize(150, 20);
        this.leaderboard.setOnAction((ActionEvent e) -> {
            GameLauncher.getScene().setRoot(LeaderboardMenu.getInstance());
        });

        this.back = new Button("Retour");
        this.back.setPrefSize(150, 20);
        this.back.setOnAction((ActionEvent e) -> {
            GameLauncher.getScene().setRoot(TitleScreen.getInstance() );
        });


        this.hBoxLevels.getChildren().addAll(startlevel, freemode, leaderboard, back);
    }
}
