package com.app.visualizationbreak.Controller;


import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.Model.gameorganisator.Game;
import com.app.visualizationbreak.View.panel.LevelSelect;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PlayerCreationListener implements EventHandler {
    private final VBox vb ;
    private final TextField textField ;

    public PlayerCreationListener(VBox vb, TextField textField){
        this.vb = vb ;
        this.textField = textField ;
    }

    @Override
    public void handle(Event event) {
        if (textField.getText().isEmpty()){
            Label errorMessage = new Label("Veuillez entrer un nom s'il vous plait");
            errorMessage.setStyle("-fx-text-fill: white;");
            vb.getChildren().addAll(errorMessage);
        }
        else{
            Game.getInstance().setPlayerName(textField.getText());
            GameLauncher.getScene().setRoot(new LevelSelect());
        }
    }
}
