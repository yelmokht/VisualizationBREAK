package com.app.visualizationbreak.Model.figures;


import com.app.visualizationbreak.Controller.observers.BigTowerEntering;
import com.app.visualizationbreak.Controller.observers.BigTowerObserving;
import com.app.visualizationbreak.Model.gameorganisator.Level;
import com.app.visualizationbreak.Model.gameorganisator.Wave;
import com.app.visualizationbreak.Model.player.Player;
import com.app.visualizationbreak.Model.threads.PNJDyingThread;
import com.app.visualizationbreak.Model.threads.PNJThread;
import com.app.visualizationbreak.Model.threads.RemoveThread;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PNJ extends Shape implements BigTowerEntering {
    private final Point originPoint;      // La map du niveau impose un point d'origine
    private final Point currentPoint ;    // Position du PNJ, qui varie
    private char type ;             // Type du PNJ défini par une lettre. Le type définira les attributs ci-dessous
    private double speed;
    private int life, reward, malus ;
    private double size ;           // Deuxième vrai attribut
    private Color color ;           // Troisième attribut
    private final ArrayList<Point> pathPoints ;
    private final javafx.scene.shape.Circle c;
    private final Wave currentWave ;      // Un PNJ nait au sein d'une vague
    private final Level currentLevel ;    // Un PNJ nait au sein d'un niveau
    private final ArrayList<BigTowerObserving> observers = new ArrayList<BigTowerObserving>() ;//liste des observers
    private final PNJThread t ;
    private boolean isTest = false, dead = false ;

    // premier constructeur : créer un PNJ selon un type (utilisé dans les tests unitaires)
    public PNJ(char type, Wave currentWave, int pathToFollow) {
        super(currentWave.getCurrentLevel().getOrPoints().get(pathToFollow));
        this.currentWave = currentWave ;
        this.type = type ;
        switch (type){
            case 'A' :          // Fat PNJ
                speed = 1 ;
                color = Color.ORANGERED;
                size = 9 ;
                life = 20 ;
                malus = 10 ;
                break ;
            case 'B' :          // Speed PNJ
                speed = 5 ;
                color = Color.MAGENTA;
                size = 6 ;
                life = 12 ;
                malus = 5;
                break ;
        }
        this.currentLevel = currentWave.getCurrentLevel() ;
        this.pathPoints = currentLevel.getPathPoints().get(pathToFollow) ;
        this.originPoint = currentLevel.getOrPoints().get(pathToFollow) ;
        this.currentPoint = new Point(originPoint.getX(), originPoint.getY()) ;
        c = new javafx.scene.shape.Circle();
        c.setCenterX(currentPoint.getX());
        c.setCenterY(currentPoint.getY());
        c.setRadius(size);
        c.setFill(color);
        this.attach(Player.getInstance());     //comme il n'y a qu'un seul Player, pas besoin de le mettre en arguments
        t = new PNJThread(this);
        t.start();
    }

    // deuxieme constructeur, utilisé dans la factory
    public PNJ(int speed, Color color, int size, int life, int malus, Wave currentWave, int pathToFollow) {
        super(currentWave.getCurrentLevel().getOrPoints().get(pathToFollow));
        this.currentWave = currentWave ;
        this.speed = speed ;
        this.size = size;
        this.life = life + 2*currentWave.getCurrentLevel().getNumber() + currentWave.getWavenumber() ;
        this.malus = malus ;
        this.currentLevel = currentWave.getCurrentLevel() ;
        this.pathPoints = currentLevel.getPathPoints().get(pathToFollow) ;
        this.originPoint = currentLevel.getOrPoints().get(pathToFollow) ;
        this.currentPoint = new Point(originPoint.getX(), originPoint.getY()) ;
        c = new javafx.scene.shape.Circle();
        c.setCenterX(currentPoint.getX());
        c.setCenterY(currentPoint.getY());
        c.setRadius(size);
        c.setFill(color);
        this.attach(Player.getInstance());     //comme il n'y a qu'un seul Player, pas besoin de le mettre en arguments
        t = new PNJThread(this);
        t.start();
    }

    public void setSpeed(double nspeed){
        if (nspeed < 0.2){
            speed = 0.2;
        }
        else {
            this.speed = nspeed;
        }
    }

    public void isHurt(int damage) throws InterruptedException {setLife(life - damage);
    }
    private double distanceToCenter(Point p){
        return this.currentPoint.distance(p);
    }
    @Override
    public void setOrigin(Point point) {
        currentPoint.setX(point.getX());
        currentPoint.setY(point.getY());
    }
    @Override
    public boolean isOn(Point p) {
        return distanceToCenter(p)<size;
    }
    @Override
    public Node getShape() {
        return c;
    }
    @Override
    public void update() {
        c.setCenterY(currentPoint.getY());
        c.setCenterX(currentPoint.getX());
    }

    public Point getCenter(){return this.currentPoint ;}

    public void moveToPoint(Point point) {
        double deltaX = point.getX() - this.currentPoint.getX() ;
        double deltaY = point.getY() - this.currentPoint.getY() ;
        if (Math.abs(deltaX) != 0){moveX(Math.signum(deltaX));}
        if (Math.abs(deltaY) != 0){moveY(Math.signum(deltaY));}
    }

    public void moveY(double signum) {currentPoint.setY(currentPoint.getY() + signum);}
    public void moveX(double signum) {currentPoint.setX(currentPoint.getX() + signum);}

    public int getLife(){
        return this.life;
    }
    public void setLife(int life) throws InterruptedException {
        if(life<=0){
            this.life = 0 ;
            die();
        }
        else{
            this.life = life ;
        }
    }


    @Override
    public void attach(BigTowerObserving o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for (BigTowerObserving o : observers){
            o.update(malus);
        }
    }

    public double getSpeed() {return speed ;}
    public Level getCurrentLevel(){return currentLevel ;}
    public ArrayList<Point> getPathPoints() { return pathPoints;
    }

    public Wave getCurrentWave() {
        return currentWave;
    }

    public void die() throws InterruptedException {
        if(!isTest && dead == false){
            PNJDyingThread thread = new PNJDyingThread(this);
            thread.start();
            thread.join();
            dead = true ;
        }

    }
    public void entersTower(){
        notifyObservers();
    }

    public void remove() throws InterruptedException {
        RemoveThread rT = new RemoveThread(this) ;
        rT.start();
        rT.join();
    }

    public void setIsTest(boolean x){isTest = x ;}

    public int getMalus() {return malus;}
}
