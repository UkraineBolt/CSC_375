/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author alex
 */
public class Point {
    public ReentrantLock lock;
    volatile int x,y;
    private float rateOfProduction;
    private final Color color;
    private final String id;
    Point(int x,int y,float rateOfProduction){
        this.x=x;this.y=y;this.rateOfProduction=rateOfProduction;
        float r = new Random().nextFloat();
        float g = new Random().nextFloat();
        float b = new Random().nextFloat();
        color = new Color(r, g, b);
        id = Float.toString(r)+Float.toString(g)+Float.toString(b);
        lock = new ReentrantLock();
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
