/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author alex
 */
public class Point {
    volatile int x,y;
    private int startX,startY;
    private float rateOfProduction;
    private Color color;
    private String id;
    Point(int x,int y,float rateOfProduction){
        this.x=x;this.y=y;this.rateOfProduction=rateOfProduction;
        startX=x;startY=y;
        float r = new Random().nextFloat();
        float g = new Random().nextFloat();
        float b = new Random().nextFloat();
        color = new Color(r, g, b);
        id = Float.toString(r)+Float.toString(g)+Float.toString(b);
    }
    public float returnRateOfProduction(){return rateOfProduction;}
    
    public synchronized boolean changePosition(int x,int y){
        this.x = x; this.y = y;
        return true;
    }
    
    @Override
    public String toString(){
        return "X";
    }
    
    
}
