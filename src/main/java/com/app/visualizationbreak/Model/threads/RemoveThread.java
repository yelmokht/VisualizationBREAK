package com.app.visualizationbreak.Model.threads;

import com.app.visualizationbreak.Model.figures.PNJ;

public class RemoveThread extends Thread {
    private final PNJ pnj ;

    public RemoveThread(PNJ pnj){
        this.pnj = pnj;
    }

    public void run(){
        pnj.getCurrentLevel().removePNJ(pnj);
        pnj.getCurrentWave().removePNJ(pnj);
    }
}
