package com.app.visualizationbreak.Model.threads;

import com.app.visualizationbreak.Model.gameorganisator.Level;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.ingameobjects.Farmer;
import com.app.visualizationbreak.Model.player.Player;

public class FarmerThread extends Thread {
    private final Farmer farmer;
    private final Level level;
    private boolean threadState;

    public FarmerThread(Farmer farmer, Level level) {
        this.farmer = farmer;
        this.level = level;
        this.threadState = level.isRunning();
    }

    public void run() {
        while(Player.getInstance().stillAlive() & threadState) {
            while (Party.getGameSpeed() == 0 || !Party.getInstance().getWavesState()){  //on ne fait rien tant qu'on est
                                                                                        //en pause OU en dehors d'une vague
                try{
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            farmer.farm();    //va rajouter l'argent
            try {
                sleep((long)(1000/ farmer.getProductionRate() / Party.getGameSpeed()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        this.threadState = false;
    }
}
