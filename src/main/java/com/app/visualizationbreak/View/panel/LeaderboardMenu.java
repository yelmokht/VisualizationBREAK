package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.utilities.PaneMeth;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class LeaderboardMenu extends BorderPane {
    private static LeaderboardMenu instance ;
    private HBox hBox;
    private LeaderboardMenu(){
        super();
        PaneMeth.setImageMenu(this);
        setHBoxLevels();
        this.setCenter(hBox);
        hBox.setAlignment(Pos.CENTER);
    }

    public static LeaderboardMenu getInstance(){
        if(instance == null){
            instance = new LeaderboardMenu();
        }
        return instance ;
    }

    private void setHBoxLevels() {
        this.hBox = new HBox();
        this.hBox.setSpacing(20);

        Button regular = new Button("Overall adventure");
        regular.setPrefSize(150, 20);
        regular.setOnAction((ActionEvent e) -> {
            try {
                GameLauncher.getScene().setRoot(Leaderboard.getInstance("regular"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        hBox.getChildren().add(regular);
        for(int i = 1 ; i <= 6 ; i ++){
            Button levelButton = new Button("Map " + i) ;
            levelButton.setPrefSize(150, 20);
            int finalI = i;
            levelButton.setOnAction((ActionEvent e) -> {
                try {
                    GameLauncher.getScene().setRoot(Leaderboard.getInstance(String.valueOf(finalI)));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            hBox.getChildren().add(levelButton);
        }

        Button freedrawing = new Button("Free map") ;
        freedrawing.setPrefSize(150, 20);
        freedrawing.setOnAction((ActionEvent e) -> {
            try {
                GameLauncher.getScene().setRoot(Leaderboard.getInstance("freedrawing") );
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Button back =new Button("Retour");
        back.setPrefSize(150, 20);
        back.setOnAction((ActionEvent e) -> {
            GameLauncher.getScene().setRoot(LevelSelect.getInstance() );
        });


        this.hBox.getChildren().addAll(freedrawing, back);
    }

}
