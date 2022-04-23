package com.app.visualizationbreak.Model.figures;
public class Point {
    public double x;
    public double y;

    public  Point (double x, double y){
        this.x = x;
        this.y = y;
    }

    public double distance(Point p){
        double d =  Math.sqrt(Math.pow((x-p.x), 2) + Math.pow((y - p.y), 2));
        return d;
    }

    public double getX(){
        return x; // x.getValue();
    }

    public void setX(double v){
        x = v; //.setValue(v);
    }

    public void setY(double v){
        y = v; //.setValue(v);
    }

    public double getY(){
        return y; //.getValue();
    }

    public String toString(){
        String str = "" ;
        str += "[x : " + this.getX();
        str += " y : " + this.getY() + " ]" ;
        return str ;
    }

}


