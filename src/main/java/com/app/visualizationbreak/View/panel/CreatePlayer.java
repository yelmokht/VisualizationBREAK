package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Controller.PlayerCreationListener;
import com.app.visualizationbreak.utilities.PaneMeth;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CreatePlayer extends BorderPane {
    private static CreatePlayer instance = null;


    private CreatePlayer() {
        super();
        PaneMeth.setImageMenu(this);
        Label label = new Label("Quel est votre nom ?");
        label.setStyle("-fx-text-fill: white;");
        TextField textField = new TextField();
        textField.setPromptText("Nom du joueur");
        VBox vb = new VBox();
        vb.setSpacing(10);
        vb.setPadding(new Insets(120, 120, 120, 120));     //Rectangle qui entoure vbox pour créer une
                                                                            //distance en px dans toutes les directions
                                                                            // (top,right,bottom,left)
        vb.setAlignment(Pos.CENTER);
        Button btn = new Button("Suivant");
        btn.setPrefSize(150, 20);
        btn.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(label, textField, btn);
        this.setCenter(vb);
        btn.setOnAction(new PlayerCreationListener(vb, textField));
    }

    public static CreatePlayer getInstance() {
        if (CreatePlayer.instance == null) {
            CreatePlayer.instance = new CreatePlayer();
        }
        return CreatePlayer.instance;
    }

    public static void setInstance(CreatePlayer instance) {
        CreatePlayer.instance = instance;
    }
}
