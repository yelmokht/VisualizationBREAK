package com.app.visualizationbreak.Model.ingameobjects;

import com.app.visualizationbreak.Model.figures.PNJ;
import com.app.visualizationbreak.Model.figures.Point;
import com.app.visualizationbreak.Model.figures.Projectile;
import com.app.visualizationbreak.Model.gameorganisator.Level;
import javafx.application.Platform;

public class ClassicalTower extends Tower {
    private int dpb;
    private final double initDPB;
    
    public ClassicalTower(Point originPoint, double fireRate, int damagePerBullet, double range, int price, int upgradePrice, int removePrice, Level level){
        super(originPoint,fireRate,range,price,upgradePrice,removePrice,level);
        initDPB = dpb ;
        dpb = damagePerBullet ;
    }

    public void attack() throws InterruptedException {
        pnjs = alivePNJs() ;
        PNJ closestPNJ = findClosestPNJ();
        if(closestPNJ != null){
            if(withinRange(closestPNJ)){
                Projectile launchedProjectile = new Projectile(originPoint, closestPNJ, level.getParty().getTilesSize()) ;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        level.getDrawing().addProjectile(launchedProjectile);
                    }
                });
                launchedProjectile.waitForIt();  //methode qui fait bouger le projectile tant qu'il n'a pas atteint la cible
                closestPNJ.isHurt(dpb);          //retire de la vie au pnj
            }
        }

    }

    private void setDPB(int damageper){
        if (damageper > 0)
            dpb = damageper;
        else {
            throw new RuntimeException("Les balles ne peuvent pas ajouter de la vie ;)");
        }
    }

    public int getDPB() {
        return dpb;
    }

    public void upgrade() {
        if (upgradeLevel < maxUpgradeLevel) {
            setDPB(dpb+5);
            setFireRate(fireRate+0.5);
            setRange(range+8);
            upgradeLevel++;
        }
    }

    public void updateLevel(int newLevel){
        dpb = (int) (initDPB + 5*(newLevel-1));
        range = initRange + 15*(newLevel-1);
        fireRate = initFireRate + 0.5*(newLevel-1);
    }
    @Override
    public double getNextStat(){
        return getDPB()+5;
    }
}
