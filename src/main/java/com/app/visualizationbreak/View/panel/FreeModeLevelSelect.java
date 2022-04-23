package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Controller.freemode.FMSwitchSceneListener;
import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.utilities.PaneMeth;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class FreeModeLevelSelect extends BorderPane {
    //private Game game;
    private Button lev1, drawMap, back;
    private HBox hBoxLevels;


    public FreeModeLevelSelect() {
        super();
        PaneMeth.setImageMenu(this);
        setHBoxLevels();
        this.setCenter(hBoxLevels);
        PaneMeth.setImageMenu(this);
        hBoxLevels.setAlignment(Pos.CENTER);
    }

    private void setHBoxLevels() {
        this.hBoxLevels = new HBox();
        this.hBoxLevels.setSpacing(20);

        for(int i = 1 ; i <= 6 ; i ++){
            Button levelButton = new Button("Map " + i) ;
            levelButton.setPrefSize(150, 20);
            levelButton.setOnAction(new FMSwitchSceneListener(i));
            hBoxLevels.getChildren().add(levelButton);
        }


        this.drawMap = new Button("Free drawing");
        this.drawMap.setPrefSize(150, 20);
        this.drawMap.setOnAction(new FMSwitchSceneListener());

        this.back = new Button("Retour");
        this.back.setPrefSize(150, 20);
        this.back.setOnAction((ActionEvent e) -> {
            GameLauncher.getScene().setRoot(LevelSelect.getInstance() );
        });


        this.hBoxLevels.getChildren().addAll(drawMap, back);
    }
}
