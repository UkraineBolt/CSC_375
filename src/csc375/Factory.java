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
public class Factory{

    private final Point[][] board;
    private final Point[] points;

    Factory(int length, int width, int machinesToUse) {
        int addedMachines = 0;
        points = new Point[machinesToUse];
        Point[][] emptyBoard = new Point[width][length];
        if (length*width!=machinesToUse) {
            while (addedMachines < machinesToUse) {
                int ranx = new Random().nextInt(length);
                int rany = new Random().nextInt(width);
                if (emptyBoard[rany][ranx] == null) {
                    Point point = new Point(ranx, rany, new Random().nextFloat());
                    emptyBoard[rany][ranx] = point;
                    points[addedMachines] = point;
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
    
    public Point[][] getFactory(){
        return board;
    }
    
    public Point[][] multiThreadedSwapping(){
        
        return getFactory();
    }
    private double affinity(Point point){
        
        return 0.0;
    }
    /*
    shit you need to do to get this to work
    see direction of most positive A {top,left,right,bottom}
    move in that direction and if lock then swap otherwise move and delete old reference
    */

}
