package com.app.visualizationbreak.Model.threads;


import com.app.visualizationbreak.Model.gameorganisator.Level;
import com.app.visualizationbreak.Model.gameorganisator.Wave;
import com.app.visualizationbreak.Model.player.Player;

import java.io.IOException;

public class LevelThread extends Thread{
    private final Level level ;
    private final int wavesNum ;

    public LevelThread(Level level){
        this.level = level ;
        this.wavesNum = this.level.getMaxWaveNumber() ;
    }

    public void run(){
        int i = 1 ;
        while(i<= wavesNum && Player.getInstance().stillAlive()){
            Wave currentWave = null;
            try {
                currentWave = level.createWave(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                currentWave.getWThread().join();
                sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++ ;
        }
        while(level.getPNJs().size()!=0){
            try{
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            level.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
