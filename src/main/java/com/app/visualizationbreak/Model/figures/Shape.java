package com.app.visualizationbreak.Model.figures;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected Point origin;
    protected Color color;
    static int LEFT = 1;
    static int RIGHT = 2;
    static int UP = 3;
    static int DOWN = 4;
    protected Point topLeft, topRight, bottomLeft, bottomRight;

    public Shape(Point origin) {
        this.origin = origin;
    }

    public void setColor(Color col){this.color = col ;}

    public abstract void setOrigin(Point point);

    public abstract boolean isOn(Point point);

    public abstract void update();

    public abstract Node getShape();

    public Point getOrigin(){return this.origin ;}

}
