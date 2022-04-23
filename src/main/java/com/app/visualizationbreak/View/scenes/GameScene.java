package com.app.visualizationbreak.View.scenes;


import com.app.visualizationbreak.Model.gameorganisator.Game;
import com.app.visualizationbreak.View.panel.GamePlay;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GameScene extends Scene {
    private static GameScene instance = null;
    private static final int width = 1280;
    private static final int height = 720;
    private static final Parent root = new AnchorPane(); //Initialise le root sinon error

    private GameScene(String mode) throws IOException {
        super(root,width, height);
        GamePlay.setInstance(null);                //s'assure de changer de gamePlay et pas remettre le meme
        Game.getInstance().initParty(mode);
        this.setRoot(GamePlay.getInstance());
        //GameLauncher.getStage().sizeToScene();
        //GameLauncher.getStage().setFullScreen(true);
    }

    public static GameScene getInstance(String mode) throws IOException {
        if (GameScene.instance == null) {
            GameScene.instance = new GameScene(mode);
        }
        return GameScene.instance;
    }

    public static void setInstance(GameScene g) {
        GameScene.instance = g;
    }
}
