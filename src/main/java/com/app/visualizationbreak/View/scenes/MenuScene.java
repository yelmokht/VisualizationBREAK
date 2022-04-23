package com.app.visualizationbreak.View.scenes;

import com.app.visualizationbreak.View.panel.TitleScreen;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class  MenuScene extends Scene {
    private static MenuScene instance = null;
    private static final int width = 800;
    private static final int height = 600;
    private static final Parent root = new AnchorPane(); //Initialise le root sinon error

    private MenuScene() {
        super(root,width, height);
        this.setRoot(TitleScreen.getInstance());
    }

    public static MenuScene getInstance() {
        if (MenuScene.instance == null) {
            MenuScene.instance = new MenuScene();
        }
        return MenuScene.instance;
    }

}
