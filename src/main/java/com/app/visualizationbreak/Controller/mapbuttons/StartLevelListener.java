package com.app.visualizationbreak.Controller.mapbuttons;

import com.app.visualizationbreak.Model.gameorganisator.Level;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.player.Player;
import com.app.visualizationbreak.View.panel.GamePlay;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class StartLevelListener implements EventHandler<ActionEvent> {
    private Level level;
    private final GamePlay gP;
    private final Button startButton;

    public StartLevelListener(GamePlay gamePlay, Button button) {
        gP = gamePlay;
        startButton = button;
    }

    @Override
    public void handle(ActionEvent event) {
        level = Party.getInstance().getCurrentLevel();
        if (level.isFinished()) {
            level.startLevel();
        }
        Party.getInstance().setWavesState(true);
        Player.getInstance().setInWave(true);    //on previent le player qu'on est rentre dans une vague.
                                                //De ce fait, le thread des ressources peut se lancer

    }
}
