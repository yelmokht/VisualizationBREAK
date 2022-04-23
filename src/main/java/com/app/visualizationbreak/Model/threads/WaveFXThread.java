package com.app.visualizationbreak.Model.threads;


import com.app.visualizationbreak.Model.figures.PNJ;
import com.app.visualizationbreak.Model.gameorganisator.Wave;

public class WaveFXThread extends Thread {
    private final PNJ pnj ;
    private final Wave wave ;

    public WaveFXThread(Wave wave, PNJ pnj){
        this.wave = wave ;
        this.pnj = pnj ;
    }

    public void run(){
        wave.addPNJ(pnj);
    }
}
