/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
ThreadLocalRandom.current().nextInt();
 */
package csc375;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public Point[][] multiThreadingSwap(int threads, int iterations){
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        for(int i=0;i<iterations;i++){
            pool.submit(new CustomRunnable(board));
        }
        pool.shutdown();
        
        return getFactory();
    }

    /*
    shit you need to do to get this to work
    see direction of most positive A {top,left,right,bottom}
    move in that direction and if lock then swap otherwise move and delete old reference
     */
    private class CustomRunnable implements Runnable{
        Point[][] board;
        CustomRunnable(Point[][] a){
            board = a;
        }
        
        private double affinity(Point point, Point point2) {
            double affinity = -2;
            double distance = Math.abs((point.x - point2.x) / (point.y - point2.y));
            affinity = Math.sin(distance / Math.pow(point.returnRateOfProduction() * point2.returnRateOfProduction(), 2));
            return affinity;
        }
        private void swap(Point p, int newx, int newy){
            if(board[newx][newy]==null){
                board[p.x][p.y] = null;
                board[newx][newy] = p;
            }else{
                Point temp = board[newx][newy];
                board[newx][newy] = p;
                board[p.x][p.y] = temp;
            }
        }
        @Override
        public void run() {
            Point point;
            int x,y;
            while(true){
                x = ThreadLocalRandom.current().nextInt(board.length-1);
                y = ThreadLocalRandom.current().nextInt(board[0].length-1);
                point = board[x][y];
                if(point!=null){
                    break;
                }
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
                } catch (InterruptedException ex) {
                    
                }
            }
            if(point.x-1!=0){
                swap(point,point.x-1,point.y);
            }
            
            
            
        }
    }
}
