package com.app.visualizationbreak.Model.threads;

import com.app.visualizationbreak.Model.figures.PNJ;
import com.app.visualizationbreak.Model.figures.Point;
import com.app.visualizationbreak.Model.figures.Projectile;

public class ProjectileThread extends Thread {
    private final int speed ;
    private final Point  currentPoint ;
    private final Projectile projectile;
    private final PNJ target ;
    private final Point fixedTarget ;

    public ProjectileThread(Projectile projectile) {
        this.projectile = projectile ;
        this.target = projectile.getTarget() ;
        fixedTarget = new Point(target.getCenter().getX(), target.getCenter().getY()) ;
        this.currentPoint = projectile.getCurrentPoint() ;
        this.speed = projectile.getSpeed() ;
    }

    public void run(){
            while(!projectile.isOn(fixedTarget)){
                projectile.move();           //bouge tant qu'il n'est pas sur la cible fixée
                try{
                    sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            projectile.setTouchedState(true); //une fois la cible touchée, son etat est changé
    }

}
