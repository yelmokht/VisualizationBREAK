package com.app.visualizationbreak.Controller.mapbuttons;

import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.View.panel.GamePlay;
import javafx.event.Event;
import javafx.event.EventHandler;

public class NewLevelListener implements EventHandler {
    private final GamePlay gP ;
    private int currentLevelNumber ;
    private final int maxLevelNumber ;

    public NewLevelListener(GamePlay gamePlay) {
        gP = gamePlay ;
        maxLevelNumber = Party.getInstance().getMaxLevelNumber();
    }

    @Override
    public void handle(Event event) {

        // afin d'éviter une nullPointerException
        if(Party.getInstance().getCurrentLevel() != null){
            currentLevelNumber = Party.getInstance().getCurrentLevel().getNumber();
        }
        else{
            currentLevelNumber = 0 ;
        }
        if(!Party.getInstance().getState()){
            Party.getInstance().makeBegin();
        }
        // on charge un nouveau niveau que si on a pas atteint la limite !
        if (currentLevelNumber < maxLevelNumber) {
            try {
                Party.getInstance().entersNextLevel(currentLevelNumber) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
            gP.updateDrawing();
        }
    }
}
