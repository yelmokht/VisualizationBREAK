package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.utilities.PaneMeth;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Tutorial extends BorderPane {
    private final VBox labelBox ;
    private Label label;
    private static String tuto ;
    private String filename ;
    private static Tutorial instance ;
    private Tutorial(String tuto) throws IOException {
        PaneMeth.setImageMenu(this);
        Tutorial.tuto = tuto ;
        labelBox = new VBox();
        labelBox.setSpacing(100);
        labelBox.setPadding(new Insets(120, 120, 120, 120));
        initLabel();
        Button back = new Button("Back") ;
        back.setPrefSize(150, 20);
        back.setOnAction((ActionEvent) ->{
            GameLauncher.getScene().setRoot(TitleScreen.getInstance()) ;
        });
        label.setStyle("-fx-text-fill: white;");
        back.setAlignment(Pos.CENTER);
        labelBox.getChildren().add(back);
        setCenter(labelBox);
    }

    public static Tutorial getInstance(String t) throws IOException {
        if(instance == null | tuto != t) {
            instance = new Tutorial(t);
        }
        return instance ;
    }

    public void initLabel() throws IOException {
        switch (tuto){
            case "general" :
                filename = "src\\main\\java\\com\\app\\visualizationbreakbreak\\View\\tutorial\\general.txt";
                break;
            case "draw" :
                filename ="src\\main\\java\\com\\app\\visualizationbreakbreak\\View\\tutorial\\draw.txt";
                break ;
            case "buy":
                filename = "src\\main\\java\\com\\app\\visualizationbreakbreak\\View\\tutorial\\buy.txt";
                break;
            case "score" :
                filename = "src\\main\\java\\com\\app\\visualizationbreakbreak\\View\\tutorial\\score.txt" ;
        }
        List<String> generalList = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8) ;
        String generalString = "" ;
        for(String line : generalList){
            generalString += line ;
            generalString += "\n" ;
        }
        label = new Label(generalString) ;
        labelBox.getChildren().add(label);
    }

    public Label getLabel() {
        return label;
    }
}
