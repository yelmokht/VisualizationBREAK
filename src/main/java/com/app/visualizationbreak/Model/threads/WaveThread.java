package com.app.visualizationbreak.Model.threads;

import com.app.visualizationbreak.Model.figures.PNJ;
import com.app.visualizationbreak.Model.gameorganisator.Level;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.gameorganisator.Wave;
import com.app.visualizationbreak.Model.player.Player;
import javafx.application.Platform;

public class WaveThread extends Thread{
    private final Wave wave ;
    private final int pnjNumber ;
    private final Level level ;
    private final int levelNum ;
    private final int waveNumber ;
    private long period ;

    public WaveThread(Wave wave){
        this.wave = wave ;
        this.pnjNumber = this.wave.getPnjsnumbers() ;
        this.level = this.wave.getCurrentLevel() ;
        this.waveNumber = this.wave.getWavenumber() ;
        levelNum = level.getNumber();
        period = (long) (1000*(1-waveNumber*levelNum*levelNum*0.01)/ Party.getGameSpeed());
    }

    public void run() {
        int i = 0;
        while (i <= pnjNumber && Player.getInstance().stillAlive()) {
            for(int n = 0; n < level.getNumberOfPaths() ; n ++ ){
                // si le jeu est en pause, la création de PNJ s'arrete parce que le thread reste bloqué dans cette boucle
                while(Party.getGameSpeed() == 0){
                    try{
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                PNJ pnj = this.wave.createRandomPNJ(n);    //ca crée un pnj random
                Platform.runLater(new WaveFXThread(wave, pnj));
            }

            try {
                if(period<=0){
                    period = 120;
                }
                sleep(period);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
