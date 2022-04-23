package com.app.visualizationbreak.Model.figures;

/** source : TP5 **/

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Circle extends Shape {
    private final javafx.scene.shape.Circle c ;
    private double radius ;

    public Circle(Point origin, Color color, double radius) {
        super(origin);
        this.c = new javafx.scene.shape.Circle(origin.getX(), origin.getY(), radius, color) ;
    }

    public void setOrigin(Point point) {
        this.origin.setX(point.getX());
        this.origin.setY(point.getY());
    }
    private double distanceToCenter(Point p){
        return this.origin.distance(p);
    }

    @Override
    public boolean isOn(Point p) {
        return distanceToCenter(p)<radius;
    }

    @Override
    public void update() {
        this.c.setCenterX(origin.getX());
        this.c.setCenterY(origin.getY());
    }

    @Override
    public Node getShape() {
        return this.c;
    }

}
