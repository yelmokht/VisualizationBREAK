package com.app.visualizationbreak.View.scenes;

import com.app.visualizationbreak.View.panel.FreeModeDrawing;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FreeModeScene extends Scene {
    private static final int width = 1280;
    private static final int height = 1720;
    private static final Parent root = new AnchorPane(); //Initialise le root sinon error

    public FreeModeScene() throws IOException {
        super(root,width, height);
        this.setRoot(new FreeModeDrawing(this));
    }

    public FreeModeScene(int mapNumber) throws IOException {
        super(root,width, height);
        this.setRoot(new FreeModeDrawing(this, mapNumber));
    }


}
