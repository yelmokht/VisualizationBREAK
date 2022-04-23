package com.app.visualizationbreak.Model.threads;

import com.app.visualizationbreak.Model.gameorganisator.Level;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.ingameobjects.Tower;
import com.app.visualizationbreak.Model.player.Player;

public class TowerThread extends Thread {
    private final Tower tower ;
    private final Level level ;
    private boolean threadState;


    public TowerThread(Tower tower, Level level) {
        this.tower = tower ;
        this.level = level ;
        this.threadState = level.isRunning();
    }
    /** partie 5, et 7, et puis 9, et puis 11, .... **/
    public void run(){
        while(threadState && Player.getInstance().stillAlive()){
            while (Party.getGameSpeed() == 0 || !Party.getInstance().getWavesState()) {
                try{
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                tower.attack();    //la tour est toujours en mode attaque tant qu'elle est active et que le joueur
                                   //est toujours en vie.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                sleep((long) (1000/tower.getFireRate() / Party.getGameSpeed()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                                            // la tour est active tant que le niveau n'est pas encore terminé
                                            // le niveau commence avec un attribut finished = true. Quand on appuie sur "Ready"
                                            // ca enclenche StartLevel qui met l'attribut à false
                                            // quand le niveau termine son thread, l'attribut est mis à true et le thread de la tour s'arrete
                                            // le niveau est terminé et la tour disparait
        }
    }

    public void stopThread() {
        this.threadState = false;
    }
}
