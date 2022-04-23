package com.app.visualizationbreak.View.scenes;

import com.app.visualizationbreak.View.panel.EndGame;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EndScene extends Scene {
    private static EndScene instance = null;
    private static final int width = 1280;
    private static final int height = 720;
    private static final Parent root = new AnchorPane(); //Initialise le root sinon error

    private EndScene() throws IOException {
        super(root,width, height);
        this.setRoot(EndGame.getInstance());
    }

    public static EndScene getInstance() throws IOException{
        if(EndScene.instance == null){
            EndScene.instance = new EndScene();
        }
        return EndScene.instance;
    }

    public static void setInstance(EndScene ninstance) {
        EndScene.instance = ninstance;
    }
}
