package com.app.visualizationbreak.Controller.mapbuttons;

import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.player.Player;
import com.app.visualizationbreak.View.panel.GamePlay;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

// gère l'activation et la desactivation des boutons selon si on est dans un niveau en cours, dans un niveau à l'arrêt,
// hors d'un niveau, dans un niveau terminé, etc?
public class LevelStateHandler implements EventHandler {

    private final Button nextLevel;
    private final Button startLevel;
    private boolean levelHasBegun;
    private boolean wavesHaveBegun;
    private final NewLevelListener newLevelListener;
    private final StartLevelListener startLevelListener;
    private final Label currentLevelLabel;
    private final Label currentWaveLabel;
    private final Label life;
    private final Label money;
    private final Label score;

    public LevelStateHandler(GamePlay gP, Button nextLevButton, Button startLevButton, Label currentLev, Label currentWave,
                             Label life, Label money, Label score) {

        this.nextLevel = nextLevButton;
        this.startLevel = startLevButton;
        this.currentLevelLabel = currentLev;
        this.currentWaveLabel = currentWave;
        this.life = life;
        this.money = money;
        this.score = score;
        newLevelListener = new NewLevelListener(gP);
        startLevelListener = new StartLevelListener(gP, startLevButton);
    }



    @Override
    public void handle(Event event) {
        if(Party.getInstance() != null) {
            if (Party.getInstance().getState()) {
                // on initie les Labels si la partie est en cours
                if (Party.getInstance().getCurrentLevel() == null) {
                    currentLevelLabel.setText("Level : " + 0);
                    currentWaveLabel.setText("Wave : " + 0);
                } else {
                    currentLevelLabel.setText("Level : " + Party.getInstance().getCurrentLevel().getNumber());
                    currentWaveLabel.setText("Wave : " + Party.getInstance().getCurrentLevel().getWaveNumber());
                }
                life.setText("Vie : " + Player.getInstance().getLife());
                money.setText("Argent : " + Player.getInstance().getResources().getMoney());
                score.setText("Score : " + Player.getInstance().getScore());
            }
            // si le niveau n'est pas encore fini et qu'on est dans une vague, on affiche le label de la vague
            if (Party.getInstance().getWavesState() && !Party.getInstance().getCurrentLevel().isFinished()) {
                currentWaveLabel.setText("Wave : " + Party.getInstance().getCurrentLevel().getWaveNumber());
            }
            levelHasBegun = Party.getInstance().getLevelState();
            wavesHaveBegun = Party.getInstance().getWavesState();
            if (!levelHasBegun) {
                Player.getInstance().makeNotInWave();
                try {
                    GamePlay.getInstance().getPlayerInterface().enableNewLevel(newLevelListener);
                    GamePlay.getInstance().getPlayerInterface().disableStartLevel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Player.getInstance().makeNotInWave();
                try {
                    GamePlay.getInstance().getPlayerInterface().disableNewLevel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!wavesHaveBegun && Party.getGameSpeed() != 0) {
                    Player.getInstance().makeNotInWave();
                    try {
                        GamePlay.getInstance().getPlayerInterface().enableStartLevel(startLevelListener);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Player.getInstance().makeInWave();
                    try {
                        GamePlay.getInstance().getPlayerInterface().disableStartLevel();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}