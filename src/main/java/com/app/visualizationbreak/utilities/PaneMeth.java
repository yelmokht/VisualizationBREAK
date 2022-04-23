package com.app.visualizationbreak.utilities;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class PaneMeth {
    public static void setImageMenu(BorderPane pane) {
        BackgroundImage ImageMenu= new BackgroundImage(new Image("file:src/main/java/com/app/visualizationbreak/View/images/tset.jpg",1920,1080,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(ImageMenu));
    }
}
