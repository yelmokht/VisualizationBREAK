package com.app.visualizationbreak.View.panel;

import com.app.visualizationbreak.Model.GameLauncher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;

public class TitleScreen extends BorderPane {
    private static TitleScreen instance = null;
    private final Media media = new Media(new File("src/main/java/com/app/visualizationbreak/View/songs/test.mp3").toURI().toString());
    private Button play, quit, t1, t2, t3, t4;
    private VBox vBoxMenu;
    private Label title;



    private TitleScreen() {
        super();
        setImageMenu();  //On met d'abord l'image
        setVBoxMenu();   //Puis les boutons
        setMediaMenu(); //Puis le son
        setTitle();
    }


    public static TitleScreen getInstance() {
        if (TitleScreen.instance == null) {
            TitleScreen.instance = new TitleScreen();
        }
        return TitleScreen.instance;
    }

    private void setVBoxMenu() {
        this.vBoxMenu = new VBox();
        this.vBoxMenu.setSpacing(20); //entre les boutons

        this.play = new Button("Play Game");
        this.play.setPrefSize(150, 20);
        this.play.setOnAction((ActionEvent e) -> {
            GameLauncher.getScene().setRoot(CreatePlayer.getInstance());
        });

        this.t1 = new Button("How to play") ;
        t1.setPrefSize(150, 20);
        t1.setOnAction((ActionEvent e) -> {
            try {
                GameLauncher.getScene().setRoot(Tutorial.getInstance("general"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        this.t2 = new Button("How to draw") ;
        t2.setPrefSize(150, 20);
        t2.setOnAction((ActionEvent e) -> {
            try {
                GameLauncher.getScene().setRoot(Tutorial.getInstance("draw"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        this.t3 = new Button("How to buy items") ;
        t3.setPrefSize(150, 20);
        t3.setOnAction((ActionEvent e) -> {
            try {
                GameLauncher.getScene().setRoot(Tutorial.getInstance("buy"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        this.t4 = new Button("How is my score saved") ;
        t4.setPrefSize(150, 20);
        t4.setOnAction((ActionEvent e) -> {
            try {
                GameLauncher.getScene().setRoot(Tutorial.getInstance("score"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        this.quit = new Button("Quit Game");
        this.quit.setPrefSize(150, 20);
        this.quit.setOnAction((ActionEvent e) -> {
            Platform.exit();
        });


        this.vBoxMenu.getChildren().addAll(play, t1, t2, t3, t4, quit);
        this.vBoxMenu.setPadding(new Insets(120, 120, 120, 120)); //Rectangle qui entoure vbox pour
        // créer une distance en px dans toutes les directions (top,right,bottom,left)
        vBoxMenu.setAlignment(Pos.BOTTOM_CENTER);
        this.setBottom(vBoxMenu);
    }

    private void setImageMenu() {
        BackgroundImage ImageMenu= new BackgroundImage(new Image("file:src/main/java/com/app/visualizationbreak/View/images/tset.jpg",1920,1080,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(ImageMenu));
    }

    private void setTitle() {
        this.title = new Label("VisualizationBREAK");
        this.title.setPadding(new Insets(120, 120, 50, 120));
        this.title.setFont(Font.loadFont("file:src/main/java/com/app/visualizationbreak/View/images/Moon2.0-Regular.otf", 72));
        this.title.setAlignment(Pos.CENTER);
        this.title.setStyle("-fx-border-color: white;");
        this.title.setStyle("-fx-text-fill: cyan;");
        setAlignment(title,Pos.CENTER);
        this.setTop(title);
    }

    private void setMediaMenu() {
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        System.out.println(mediaPlayer.getCycleCount());
        MediaView mediaView = new MediaView(mediaPlayer);
        this.getChildren().add(mediaView); }


}
