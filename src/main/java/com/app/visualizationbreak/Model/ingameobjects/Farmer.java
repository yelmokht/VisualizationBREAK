package com.app.visualizationbreak.Model.ingameobjects;

//les Farmers seront des objets que le joueur pourra acheter pour qu'il lui rapporte plus d'argent

import com.app.visualizationbreak.Model.figures.Point;
import com.app.visualizationbreak.Model.figures.Shape;
import com.app.visualizationbreak.Model.gameorganisator.Level;
import com.app.visualizationbreak.Model.player.Player;
import com.app.visualizationbreak.Model.player.Resources;
import com.app.visualizationbreak.Model.threads.FarmerThread;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;


public class Farmer extends Shape implements Buyable, Upgradable {
    private Rectangle farmer;
    private final double initProductionRate;
    private double productionRate;
    private final int initProduction;
    private int production;
    private final int cost;
    private final int upgradePrice;
    private final int removePrice;
    private int upgradeLevel;
    private final int maxUpgradeLevel;
    private final int size;
    private final Level level;
    private final Resources resources;
    private final FarmerThread farmerThread;


    public Farmer(Point point, double productionRate, int production, int cost, int upgradePrice, int removePrice, Level level) {
        super(point);

        this.productionRate = productionRate;
        this.initProductionRate = productionRate;
        this.production = production;
        this.initProduction = production;
        this.cost = cost;
        this.upgradePrice = upgradePrice;
        this.removePrice = removePrice;
        this.upgradeLevel = 1;
        this.maxUpgradeLevel = 3;
        this.size = 15;
        this.level = level;
        this.resources = Player.getInstance().getResources();
        this.setUpFarmer(point);
        this.farmerThread = new FarmerThread(this,level);
    }

    private void setUpFarmer(Point point) {
        this.farmer = new Rectangle(size,size/2);
        this.farmer.setX(point.getX());
        this.farmer.setY(point.getY());
    }

    public double getProductionRate() {
        double u = (double)Math.round(productionRate * 10d) / 10d ;
        return u;
    }
    public double getNextProdRate(){
        double u = (double)Math.round((productionRate+0.1) * 10d) / 10d ;
        return u ;
    }

    public int getProduction() {
        return this.production;
    }

    public int getUpgradePrice() {
        return this.upgradePrice;
    }

    public int getRemovePrice() {
        return this.removePrice;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    @Override
    public int getPrice() {
        return cost;
    }

    public void setProductionRate(double productionRate) {
        if (productionRate > 0)
            this.productionRate = productionRate;
        else {
            throw new RuntimeException("");
        }
    }

    public void setProduction(int production) {
        if (production > 0)
            this.production = production;
        else {
            throw new RuntimeException("");
        }
    }


    public void setUpgradeLevel(int upgradeLevel) {
        if(upgradeLevel<=maxUpgradeLevel){
            this.upgradeLevel = upgradeLevel ;
        }
        else{
            upgradeLevel = maxUpgradeLevel;
        }
        updateLevel(upgradeLevel);
    }

    public void updateLevel(int newLevel) {
        production = initProduction + (newLevel-1);
        productionRate = initProductionRate + 0.1*(newLevel-1);
    }

    public void upgrade() {
        if (upgradeLevel < maxUpgradeLevel) {
            setProduction(production+1);
            setProductionRate(productionRate + 0.1);
            upgradeLevel++;
        }
    }

    public void farm() {
        resources.addMoney(production);
    }

    public void start() {
        this.farmerThread.start();
    }

    public void remove() {
        this.farmerThread.stopThread();
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
        // pas utilisé car il s'agit d'un objet
    }

    @Override
    public Node getShape() {
        return farmer;
    }

    public int getMaxUpLevel() {
        return maxUpgradeLevel;
    }

}


