package com.app.visualizationbreak.Model.ingameobjects;

import com.app.visualizationbreak.Model.figures.PNJ;
import com.app.visualizationbreak.Model.figures.Point;
import com.app.visualizationbreak.Model.figures.Projectile;
import com.app.visualizationbreak.Model.gameorganisator.Level;
import javafx.application.Platform;

// tour qui va ralentir les pnj
public class SlowingTower extends Tower {
    private double sp;      //vitesse qu'un projectile va retirer
    private final double initsp;

    public SlowingTower(Point originPoint, double fireRate, double speedPerBullet, double range, int price, int upgradePrice, int removePrice, Level level) {
        super(originPoint, fireRate, range, price, upgradePrice, removePrice, level);
        initsp = sp;
        sp = speedPerBullet;
    }

    public void attack() throws InterruptedException {
        pnjs = alivePNJs();
        PNJ closestPNJ = findClosestPNJ();
        if (closestPNJ != null) {
            if (withinRange(closestPNJ)) {
                Projectile launchedProjectile = new Projectile(originPoint, closestPNJ, level.getParty().getTilesSize());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        level.getDrawing().addProjectile(launchedProjectile);
                    }
                });
                launchedProjectile.waitForIt();    //methode qui fait bouger le projectile tant qu'il n'a pas atteint la cible
                closestPNJ.setSpeed(closestPNJ.getSpeed() - this.sp);}
            }
        }


    private void setSP(double speed){
        if (speed > 0){
        sp = speed;}     //on peut seulement ralentir les pnjs, et non les ralentir
    }

    public double getSp() {
        return sp;
    }

    public void upgrade() {
        if (upgradeLevel < maxUpgradeLevel) {
            setSP(sp+0.2);
            setFireRate(fireRate+0.5);
            setRange(range+8);
            upgradeLevel++;
        }
    }

    public void updateLevel(int newLevel){
        sp = (int) (initsp + 0.2*(newLevel-1));
        range = initRange + 15*(newLevel-1);
        fireRate = initFireRate + 0.5*(newLevel-1);
    }

    @Override
    public double getNextStat(){
        return getSp()+0.2;
    }
}