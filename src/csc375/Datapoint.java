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
public class Datapoint {
    int startx,starty,currentx,currenty;
    float rateOfCapacity;
    long id;
    Datapoint(int x, int y, float rate, long id){
        currentx=startx=x;
        currenty=starty=y;
        rateOfCapacity = rate;
        this.id=id;
    }
    
    
}
