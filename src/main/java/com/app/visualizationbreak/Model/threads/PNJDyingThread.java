package com.app.visualizationbreak.Model.threads;

import com.app.visualizationbreak.Model.figures.PNJ;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.player.Player;
import javafx.application.Platform;

public class PNJDyingThread extends Thread {
    private final PNJ pnj ;

    public PNJDyingThread(PNJ pnj){
        this.pnj = pnj ;
    }

    public void run(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Party.getInstance().getCurrentLevel().getDrawing().getChildren().remove(pnj.getShape());
            }
        });
        Player.getInstance().getResources().addMoney(10);
        Player.getInstance().setScore(Player.getInstance().getScore() + pnj.getMalus());
        try {
            pnj.remove();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
