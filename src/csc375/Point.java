/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375;

/**
 *
 * @author alex
 */
public class Point {
    volatile int x,y;
    private int startX,startY;
    private float rateOfProduction;
    Point(int x,int y,float rateOfProduction){
        this.x=x;this.y=y;this.rateOfProduction=rateOfProduction;
        startX=x;startY=y;
    }
    public float returnRateOfProduction(){return rateOfProduction;}
    
    
    
}
