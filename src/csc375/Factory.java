/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
ThreadLocalRandom.current().nextInt();
 */
package csc375;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author alex
 */
public class Factory {
    private final Point[][] board;
    private volatile ArrayList<Point> allFilledPoints = new ArrayList<>();
    Factory(Factory f){
        Point[][] newBoard = new Point[f.getFactory().length][f.getFactory()[0].length];
        ArrayList<Point> listOfPoints = f.getPoints();
        allFilledPoints = new ArrayList<>();
        for(int i=0;i<listOfPoints.size();i++){
            Point newPoint = new Point(listOfPoints.get(i));
            newBoard[newPoint.x][newPoint.y] = newPoint;
            allFilledPoints.add(newPoint);
        }
        board=newBoard;
    }

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
                    allFilledPoints.add(point);
                    System.out.println(addedMachines);
                }
            }
        } else {
            for (int y = 0; y < width; y++) {
                for (int x = 0; x < length; x++) {
                    Point point = new Point(x, y, new Random().nextFloat());
                    emptyBoard[y][x] = point;
                    allFilledPoints.add(point);
                }
            }
        }
        board = emptyBoard;
    }
    
    public ArrayList<Point> getPoints(){
        return allFilledPoints;
    }

    public synchronized Point[][] getFactory() {
        return board;
    }
    
    private synchronized void swap(Point point,int newx, int newy ){
        int oldx=point.x; int oldy=point.y;
        if(newx<0 || newy<0 || newx>=board.length || newy>=board[0].length){
            System.out.println("out of bounds x or y");
            return;
        }
        if(board[newx][newy]==null){
            board[point.x][point.y]=null;
            board[newx][newy]=point;
            point.changePosition(newx, newy);
        }else{
            Point moveable = board[newx][newy];
            board[newx][newy] = point;
            board[point.x][point.y] = moveable;
            moveable.changePosition(oldx,oldy);
            point.changePosition(newx, newy);
        }
    }
    
    private void newLocation(Point loci,int direction){
        switch(direction){
            case 0://top
                this.swap(loci,loci.x,loci.y+1);
                break;
            case 1://right
                this.swap(loci,loci.x+1,loci.y);
                break;
            case 2://bot
                this.swap(loci,loci.x,loci.y-1);
                break;
            case 3://left
                this.swap(loci,loci.x-1,loci.y);
                break;
            default:
                System.out.println("invalid direction thus no swap");
                break;
        }
    }
    
    private double affinity(Point point, Point point2) {//closest to 0 wins
        double distance;
        if((point.x - point2.x)==0){
            distance = Math.abs(point.y - point2.y);
        }else if((point.y - point2.y)==0){
            distance = Math.abs(point.x - point2.x);
        }else{
            distance = Math.abs((point.y - point2.y) / (point.x - point2.x));
        }
        
        double affinity = Math.pow(point.returnRateOfProduction() * point2.returnRateOfProduction(), 2)/distance;
        return affinity;
    }
    
    public void mutation(){
        double[] mutation = {0,0,0,0}; //top,right,bot,left
        int[] totalInSector = {0,0,0,0};
        int randomPointIndex = ThreadLocalRandom.current() .nextInt(allFilledPoints.size()-1);;
        Point loci = allFilledPoints.get(randomPointIndex);
        for (Point otherPoint : allFilledPoints) {
            if(!loci.getID().equals(otherPoint.getID())){
                double a = affinity(loci,otherPoint);
                if(loci.x<=otherPoint.x){//right
                    mutation[1]+=a;
                    totalInSector[1]+=1;
                }else{//left
                    mutation[3]+=a;
                    totalInSector[3]+=1;
                }
                if(loci.y<=otherPoint.y){//top
                    mutation[0]+=a;
                    totalInSector[0]+=1;
                }else{//bot
                    mutation[2]+=a;
                    totalInSector[2]+=1;
                }
            }
        }
        for(int i=0;i<mutation.length;i++){
            mutation[i] = mutation[i]/totalInSector[i];
        }
        int direction = mutationDirection(mutation);
        if(direction==-1){return;}
        this.newLocation(loci,direction);
    }
    
    private int mutationDirection(double[] mutationDirectionAffinity){
        int x = 0;
        double initial = mutationDirectionAffinity[0];
        for(int i=1;i<mutationDirectionAffinity.length;i++){
            if(initial<=mutationDirectionAffinity[i]){
                x = i;
                initial = mutationDirectionAffinity[i];
            }
        }
        return x;
    }
    
    public double fitness(){
        double fit = 0;
        for(int i=0;i<allFilledPoints.size();i++){
            Point temp =allFilledPoints.get(i);
            for(int i2=i;i2<allFilledPoints.size();i2++){
                if(i!=i2){
                    fit += affinity(allFilledPoints.get(i), allFilledPoints.get(i2));
                }
            }
        }
        fit = fit/allFilledPoints.size();
        
        return fit;
    }
}
