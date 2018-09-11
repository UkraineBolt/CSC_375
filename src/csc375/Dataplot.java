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
public class Dataplot {
    int[] location;
    int affinity;
    Dataplot(int x, int y, int affinity){
        this.affinity=affinity;
        location = new int[] {x, y};
    }
    
    
}
