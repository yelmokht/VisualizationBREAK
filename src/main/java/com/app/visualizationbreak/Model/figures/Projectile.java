package com.app.visualizationbreak.Model.figures;

import com.app.visualizationbreak.Model.threads.ProjectileThread;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

import static javafx.scene.paint.Color.BLACK;

public class Projectile extends Shape {
    private final Point currentPoint;
    private final PNJ target ;
    private final int speed;
    private final int size ;
    private boolean hasTouched ;
    private final Circle c ;
    private final ProjectileThread t;
    private final double dx;
    private final double dy ;

    // un projectile est initié avec un point d'origine, une cible. Pour calculer l'orientation du projectile,
    // il faut la distance p/r à la cible
    public Projectile(Point origin, PNJ target, int tileWidth) {
        super(origin);
        this.currentPoint = new Point(origin.getX(), origin.getY() );
        this.target = target ;
        size = 2 ;
        c = new Circle() ;
        c.setCenterX(currentPoint.getX() + tileWidth/2);
        c.setCenterY(currentPoint.getY() + tileWidth/2);
        c.setRadius(size);
        c.setFill(BLACK);
        hasTouched = false ;
        dx = (target.getCenter().getX() - this.currentPoint.getX())/distanceToCenter(target.getCenter()) ;
        dy = (target.getCenter().getY() - this.currentPoint.getY())/distanceToCenter(target.getCenter()) ;
        this.speed = 50 ; // 10 fois plus rapide que le PNJ rapide
        t = new ProjectileThread(this) ;
        t.start();
    }

    public void move() {
        currentPoint.setX(currentPoint.getX() + 2*dx);
        currentPoint.setY(currentPoint.getY() + 2*dy);
    }



    // accesseurs
    public Point getCurrentPoint(){return currentPoint ;}
    public PNJ getTarget(){return target ;}
    public int getSpeed(){return speed;}
    public boolean hasTouched(){return hasTouched ; }
    public void setTouchedState(boolean state){hasTouched = state ;}


    public void waitForIt() throws InterruptedException {t.join();}
    @Override
    public void setOrigin(Point point) {
        currentPoint.setX(point.getX());
        currentPoint.setY(point.getY());
    }

    private double distanceToCenter(Point p){
        return this.currentPoint.distance(p);
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


}
