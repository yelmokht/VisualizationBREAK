package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Controller.LevelSelectListener;
import com.app.visualizationbreak.Controller.freemode.FMSwitchSceneListener;
import com.app.visualizationbreak.Model.GameLauncher;
import com.app.visualizationbreak.utilities.ListMeth;
import com.app.visualizationbreak.utilities.PaneMeth;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Leaderboard extends BorderPane {
    private static Leaderboard instance ;
    private final List<String> top ;
    private static String mode;
    private final String filename;
    private Label leaderLabel;

    private Leaderboard(String mode) throws IOException {
        Leaderboard.mode = mode;
        PaneMeth.setImageMenu(this);
        Button playMode = new Button("Play this mode !");
        playMode.setPrefSize(150, 20);
        switch (mode){
            case "regular" :
                filename = "src\\main\\java\\com\\app\\visualizationbreakbreak\\Model\\serialization\\regular.txt" ;
                playMode.setOnAction(new LevelSelectListener());
                break;
            case "freedrawing":
                filename = "src\\main\\java\\com\\app\\visualizationbreakbreak\\Model\\serialization\\free.txt" ;
                playMode.setOnAction(new FMSwitchSceneListener());
                break;
            default:
                int i = Integer.valueOf(mode);
                System.out.println(i);
                filename = "src\\main\\java\\com\\app\\visualizationbreakbreak\\Model\\serialization\\freemode"+mode+".txt";
                playMode.setOnAction(new FMSwitchSceneListener(i));
        }
        top = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
        ListMeth.triBulle(top);
        buildLeaderboard();


        Button back = new Button("Back");
        back.setOnAction((ActionEvent e) -> {
            GameLauncher.getScene().setRoot(LeaderboardMenu.getInstance() );
        });


        VBox vb = new VBox();
        leaderLabel.setStyle("-fx-text-fill: white;");
        leaderLabel.setFont(Font.loadFont("file:src/main/java/com/app/visualizationbreak/View/images/Moon2.0-Regular.otf", 30));
        vb.setSpacing(10);
        vb.setPadding(new Insets(120, 120, 120, 120));
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(leaderLabel, playMode, back);
        this.setCenter(vb);

    }

    public static Leaderboard getInstance(String m) throws IOException {
        if(instance == null | mode != m){
            instance = new Leaderboard(m) ;
        }
        return instance;
    }

    public void buildLeaderboard(){
        String ranking = "";
        for (int i = 0; i< top.size(); i++ ){
            ranking +=  i+1 + ". ";
            ranking += top.get(i).replace(",", " : ");
            ranking += "\n";
        }
        leaderLabel = new Label(ranking);
    }

}
