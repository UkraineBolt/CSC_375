/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375;

import java.util.Random;

/**
 *
 * @author alex
 */
public class Factory {

    private final Point[][] board;

    Factory(int length, int width, int machinesToUse) {
        int addedMachines = 0;
        Point[][] emptyBoard = new Point[width][length];
        if (length*width!=machinesToUse) {
            while (addedMachines < machinesToUse) {
                int ranx = new Random().nextInt(length);
                int rany = new Random().nextInt(width);
                if (emptyBoard[rany][ranx] == null) {
                    emptyBoard[rany][ranx] = new Point(ranx, rany, new Random().nextFloat());
                    addedMachines++;
                    System.out.println(addedMachines);
                }
            }
        } else {
            for(int y=0;y<width;y++){
                for(int x=0;x<length;x++){
                    emptyBoard[y][x] = new Point(x,y,new Random().nextFloat());
                }
            }
        }
        board = emptyBoard;
    }
    
    public Point[][] getFactory(){return board;}
    
    public void multiThreadedSwapping(){
        
    }

}
