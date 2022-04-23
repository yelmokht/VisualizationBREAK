package com.app.visualizationbreak.Model.threads;

import com.app.visualizationbreak.Model.figures.PNJ;
import com.app.visualizationbreak.Model.figures.Point;
import com.app.visualizationbreak.Model.gameorganisator.Party;
import com.app.visualizationbreak.Model.player.Player;

import java.util.ArrayList;

public class PNJThread extends Thread {
    private final PNJ pnj ;
    private int life;
    private final int maxXpos ;
    private double speed;
    private final ArrayList<Point> pathPoints ;
    private final Point finalPoint ;

    public PNJThread(PNJ pnj){
        this.pnj = pnj ;
        this.speed = pnj.getSpeed();
        this.life = pnj.getLife() ;
        this.pathPoints = pnj.getPathPoints();
        finalPoint = pathPoints.get(pathPoints.size()-1) ;
        finalPoint.setX(finalPoint.getX() + pnj.getCurrentLevel().getParty().getTilesSize()/2);
        pathPoints.add(finalPoint);
        maxXpos = pnj.getCurrentLevel().getParty().getTilesSize()*32 ;
    }

    public void run(){
        int i = 0 ;

        while(life>0 & pnj.getCenter().getX() <= maxXpos & i< pathPoints.size() && Player.getInstance().stillAlive()){
            //tant que le joueur et le pnj sont en vie et tant que ce dernier n'est pas arrivé au bout, c'est bon, le
            //thread peut être enclenché
            while(Party.getGameSpeed() == 0){
                try{
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            speed = pnj.getSpeed();
            pnj.moveToPoint(pathPoints.get(i)); // fait pas de longueur 1 vers ce point
            if((int) (pnj.getCenter().distance(pathPoints.get(i))) == 0){ // si on a atteint le point, alors on cherche le suivant
                i++ ;
            }
            try {
                Thread.sleep((long) (40 / speed / Party.getGameSpeed()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            life = pnj.getLife();
        }
     if((int) pnj.getCenter().getX() >= maxXpos){
         pnj.entersTower();
     }
        try {
            pnj.remove();   //retire le pnj de la vague et du level
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
