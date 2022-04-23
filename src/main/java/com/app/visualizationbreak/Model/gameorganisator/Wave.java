package com.app.visualizationbreak.Model.gameorganisator;


import com.app.visualizationbreak.Model.factories.PNJFactory;
import com.app.visualizationbreak.Model.figures.PNJ;
import com.app.visualizationbreak.Model.threads.WaveThread;

import java.util.ArrayList;
import java.util.Random;

public class Wave {
    private final ArrayList<PNJ> activePNJs;
    private final int wavenumber;
    private final Level currentLevel ;
    private final int pnjsnumbers;
    private final char[] pnjTypes = "ABCD".toCharArray() ;
    private final WaveThread t;


    public Wave(Level currentLevel, int wavenumber) throws Exception {
        this.currentLevel = currentLevel;
        if(wavenumber> currentLevel.getMaxWaveNumber()){
            throw new Exception("Wave number out of bounds " + currentLevel.getMaxWaveNumber());
        }
        else{
            this.wavenumber = wavenumber;
        }

        activePNJs = new ArrayList<>();
        this.pnjsnumbers = 10+4*currentLevel.getNumber() + 2*wavenumber ;
        this.t = new WaveThread(this) ;

    }
    public void launchWave(){t.start();}
    public ArrayList<PNJ> getPnjs(){return this.activePNJs;}
    public Level getCurrentLevel(){return this.currentLevel;}

    public int getPnjsnumbers(){return this.pnjsnumbers ;}
    public void addPNJ(PNJ movPNJ) {
        this.activePNJs.add(movPNJ);
        this.currentLevel.addPNJ(movPNJ);
    }


    public PNJ createRandomPNJ(int pathNumber) {
        char pnjType = pnjTypes[(new Random()).nextInt(4)] ;
        PNJ newPNJ = PNJFactory.getInstance(pnjType, this, pathNumber);
        return newPNJ;
    }

    public WaveThread getWThread(){return this.t ;}
    public int getWavenumber(){
        return wavenumber ;}
    public void removePNJ(PNJ pnj){
        activePNJs.remove(pnj);
    }
}