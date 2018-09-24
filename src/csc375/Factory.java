/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
ThreadLocalRandom.current().nextInt();
 */
package csc375;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author alex
 */
public class Factory {
    private final Point[][] board;
    

    Factory(int length, int width, int machinesToUse) {
        int addedMachines = 0;
        Point[][] emptyBoard = new Point[length][width];
        if (length * width != machinesToUse) {
            while (addedMachines < machinesToUse) {
                int ranx = new Random().nextInt(length);
                int rany = new Random().nextInt(width);
                if (emptyBoard[ranx][rany] == null) {
                    Point point = new Point(ranx, rany, new Random().nextFloat());
                    emptyBoard[ranx][rany] = point;
                    addedMachines++;
                    System.out.println(addedMachines);
                }
            }
        } else {
            for (int y = 0; y < width; y++) {
                for (int x = 0; x < length; x++) {
                    emptyBoard[y][x] = new Point(x, y, new Random().nextFloat());
                }
            }
        }
        board = emptyBoard;
    }

    public synchronized Point[][] getFactory() {
        return board;
    }
    
    public synchronized void swap(Point point,int newx, int newy ){
        int oldx=point.x; int oldy=point.y;
        if(board[newx][newy]==null){
            board[point.x][point.y]=null;
            board[newx][newy]=point;
            point.changePosition(newx, newy);
        }else{//this will need to handle for deadlocks
            Point moveable = board[newx][newy];
            board[newx][newy] = point;
            board[point.x][point.y] = moveable;
            moveable.changePosition(oldx,oldy);
            point.changePosition(newx, newy);
        }
    }
    
    private double affinity(Point point, Point point2) {
        double affinity = -2;
        double distance = Math.abs((point.x - point2.x) / (point.y - point2.y));
        affinity = Math.sin(distance / Math.pow(point.returnRateOfProduction() * point2.returnRateOfProduction(), 2));
        return affinity;
    }

    
    
}
