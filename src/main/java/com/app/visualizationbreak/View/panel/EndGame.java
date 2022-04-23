package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.player.Player;
import com.app.visualizationbreak.utilities.PaneMeth;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class EndGame extends BorderPane {
    private static EndGame instance = null;

    private EndGame() throws IOException {
        super();
        PaneMeth.setImageMenu(this);
        Label result = new Label("Classement : "+Party.getInstance().getMode());
        String ranking = "";
        int limite = Math.min(5, Party.getInstance().getTop5().size());
        //affiche seulement les 5 premiers scores du mode
        for (int i = 0; i< limite; i++ ){
            ranking +=  i+1 + ". ";
            ranking += Party.getInstance().getTop5().get(i).replace(",", " : ");
            ranking += "\n";
        }
        Label classement = new Label(ranking);

        String name = Player.getInstance().getName();
        int res = Player.getInstance().getScore();
        int resnum = 1 + Party.getInstance().getTop5().indexOf(name+","+res);
        Label votreres = new Label("Votre résultat");
        Label score = new Label(resnum +". "+ name + " : " + res);
        VBox vb = new VBox();
        vb.setSpacing(10);
        vb.setPadding(new Insets(120, 120, 120, 120));
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(result, classement, votreres, score);
        for(Node node : vb.getChildren()){
            node.setStyle("-fx-text-fill: white;");
        }
        this.setCenter(vb);

        Button btn = new Button("Quit the game");
        btn.setPrefSize(150, 20);
        btn.setAlignment(Pos.CENTER);
        vb.getChildren().add(btn);
        btn.setOnAction((ActionEvent e) -> {
            Platform.exit();
        });
    }

    public static EndGame getInstance() throws IOException {
        if (EndGame.instance == null) {
            EndGame.instance = new EndGame();
        }
        return EndGame.instance;
    }
}
