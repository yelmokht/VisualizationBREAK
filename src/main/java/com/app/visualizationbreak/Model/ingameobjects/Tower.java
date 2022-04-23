package com.app.visualizationbreak.Model.ingameobjects;


import com.app.visualizationbreak.Model.figures.PNJ;
import com.app.visualizationbreak.Model.figures.Point;
import com.app.visualizationbreak.Model.figures.Shape;
import com.app.visualizationbreak.Model.gameorganisator.Level;
import com.app.visualizationbreak.Model.threads.TowerThread;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


/** creation d'une tour partie 4**/
public abstract class Tower extends Shape implements Buyable, Upgradable, Attacking {
    protected Point originPoint ;
    protected double fireRate, range ;
    protected int upgradeLevel, maxUpgradeLevel, price, upgradePrice, removePrice ;
    protected double initFireRate, initRange ;
    protected Rectangle tower;
    protected double size = 15;
    protected Level level ;
    protected ArrayList<PNJ> pnjs ;
    protected TowerThread thread ;

    public Tower(Point originPoint, double fireRate, double range, int price, int upgradePrice, int removePrice, Level level){
        super(originPoint);
        this.originPoint = originPoint ;
        this.fireRate = fireRate ;
        this.range = range ;
        this.level = level ;
        this.price = price;
        this.upgradePrice = upgradePrice;
        this.removePrice = removePrice;
        upgradeLevel = 1 ;
        maxUpgradeLevel = 3 ;
        initFireRate = fireRate ;
        initRange = range ;
        this.tower = new Rectangle(size,size/2);
        this.tower.setX(originPoint.getX());
        this.tower.setY(originPoint.getY());
    }

    // donne l'information des PNJ sur la map
    public ArrayList<PNJ> alivePNJs(){return level.getPNJs();}

    public double getDistanceto(PNJ pnj){
        if(pnj != null){
            return originPoint.distance(pnj.getCenter());
        }
        else{
            return 0 ;
        }
    }


    // Accesseurs, setters
    public int getUpgradeLevel(){return upgradeLevel ;}
    public void setUpgradeLevel(int lev){
        if(lev<=maxUpgradeLevel){
            upgradeLevel = lev ;
        }
        else{
            upgradeLevel = maxUpgradeLevel;
        }
        updateLevel(upgradeLevel);
    }

    public double getFireRate(){return fireRate ;}
    public void setFireRate(double fR) {
        if (fR > 0) {
            fireRate = fR;
        }
        else {
            throw new RuntimeException("Pas possible d'avoir une vitesse de tir nulle ou négative");
        }
    }


    public void setRange(double range) {
        if (range > 0) {
            this.range = range;
        }
        else {
            throw new RuntimeException("Pas possible d'avoir une portée nulle ou négative");
        }
    }
    public double getRange(){return range;}

    public Point getPoint(){return originPoint ;}

    @Override
    public int getPrice() {return price ;}

    public int getUpgradePrice() {
        return this.upgradePrice;
    }

    public  int getRemovePrice() {
        return this.removePrice;
    }

    public int getMaxUpLevel() {
        return maxUpgradeLevel;
    }

    // attaque le pnj de la map le plus proche s'il est dans le range, a redéfinir
    public abstract void attack() throws InterruptedException;

    protected boolean withinRange(PNJ closestPNJ) { return getDistanceto(closestPNJ) <= range;}

    // compare la distance entre la tour et chaque PNJ. Renvoit le pnj a la distance minimale, s'il existe
    protected PNJ findClosestPNJ() {
        PNJ closestPNJ = null ;
        int indexOfClosest = 0 ;
        if(pnjs.size()!=0){
            for(int i = 0; i<pnjs.size(); i++){
                if(getDistanceto(pnjs.get(i))<=getDistanceto(pnjs.get(indexOfClosest))){
                    indexOfClosest = i ;
                }
            }
            closestPNJ = pnjs.get(indexOfClosest) ;
        }

        return closestPNJ ;
    }

    @Override
    public void setOrigin(Point point) {
    }

    @Override
    public boolean isOn(Point point) {
        return false;
    }

    @Override
    public void update() {
    }

    @Override
    public Node getShape() {
        return this.tower;
    }

    public void addAndStart() {
        level.addTower(this);
        thread = new TowerThread(this, level) ;
        thread.start();
    }

    @Override
    public void upgrade() {
    }

    public abstract void updateLevel(int newLevel);

    public void remove() {
        this.thread.stopThread();
    }


    public abstract double getNextStat();
}
