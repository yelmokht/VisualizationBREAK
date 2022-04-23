package com.app.visualizationbreak.Model.ingameobjects;

import com.app.visualizationbreak.Model.figures.Point;

public abstract class GameObject {
    private double x;
    private double y;

    //Constructor
    public GameObject(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    //methode qui permet de fournir un point à partir de la position des objets
    public Point getPoint(){
        Point p = new Point(this.getX(), this.getY());
        return p;
    }

}
