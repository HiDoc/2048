/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grid;

import tile.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Flo
 */
public class Grid {
    final Tile[] myGrid;
    private Tile[] lastGrid;
    final int col;
    final int row;
    
    public Grid(){
        this.col = 4;
        this.row = 4;
        this.myGrid = initialiseGrid();
        this.lastGrid = initialiseGrid();
    }
    public Grid(int row, int col){
        this.col = col;
        this.row = row;
        this.myGrid = initialiseGrid();
        this.lastGrid = initialiseGrid();
    }
    
    private Tile[] initialiseGrid(){
        Tile[] newGrid = new Tile[this.col * this.row];
        for (int i = 1; i <= this.row; i++) {
            for (int j = 1; j <= this.col; j++) {
                newGrid[(j-1) + ((i-1) * this.row)] = new Tile(i, j);
            }
        }
        return newGrid;
    }
    
    private Tile[] mergeLine(Tile[] myGrid,Tile[] LineTile){
        for (int i = 0; i < LineTile.length; i++) {
            myGrid[getArrayIndex(LineTile[i])].setValue(LineTile[i].getValue());
        }
        return myGrid;
    }
    
    protected Tile[] extractLine(Tile[] gridTile, int lineNumber){
        Tile[] newLine = new Tile[this.col];
        for (int i = 0; i < this.col; i++) {
            newLine[i] = gridTile[((lineNumber-1)*this.row) + i];
        }
        return newLine;
    }
    protected int getArrayIndex(Tile Tile){
        return ((Tile.getAbs()-1) + ((Tile.getOrd() - 1) * this.col )); 
    }
    private int getArrayIndex(int abs, int ord){
        return ( abs - 1 ) + (ord - 1) * this.col ; 
    }
    
    protected Tile[] removeSpace(Tile[] LineTile){
        for (int i = 0; i < LineTile.length - 1; i++) {
            if (LineTile[i].isNull()) {
                LineTile[i].setValue(LineTile[i+1].getValue());
            }
        }
        return LineTile;
    }
    protected Tile[] mergeTile(Tile[] LineTile){
        for (int i = 0; i < LineTile.length - 1; i++) {
            if (LineTile[i].sameValue(LineTile[i+1])) {
                LineTile[i].setValue(LineTile[i].getValue() * 2);
                LineTile[i+1].setValue(0);
            }
        }
        return LineTile;
    }
    
    protected void rotateGrid(int numberOfRotation){
        for (int a = 0; a < numberOfRotation; a++) {
            int n=this.row;
            int tmp;
            for (int i=0; i<n/2; i++){
                for (int j=i; j<n-i-1; j++){
                        tmp = myGrid[getArrayIndex(i+1,j+1)].getValue();
                        myGrid[getArrayIndex(i+1,j+1)].setValue(myGrid[getArrayIndex(j+1,n-i)].getValue());
                        myGrid[getArrayIndex(j+1,n-i)].setValue(myGrid[getArrayIndex(n-i,n-j)].getValue());
                        myGrid[getArrayIndex(n-i,n-j)].setValue(myGrid[getArrayIndex(n-j,i+1)].getValue());
                        myGrid[getArrayIndex(n-j,i+1)].setValue(tmp);
                }
            }
        }
    }
    private ArrayList availableSpace() {
        ArrayList<Integer> availableSpace = new ArrayList<>();
        for (int i = 0; i < myGrid.length; i++) {
            if (myGrid[i].isNull()) {
                availableSpace.add(i);
            }
        }
        Collections.shuffle(availableSpace);
        return availableSpace;
    }
    public void addTile(){
        myGrid[(int)availableSpace().get(0)].setValue(2);
    }   
    private void moveGrid(){
        for (int i = 1; i <= this.row; i++) {
            mergeLine(myGrid, removeSpace(mergeTile(removeSpace(extractLine(myGrid, i)))));
        }
    }
    public void direction(int directionValue){
        rotateGrid(directionValue);
        moveGrid();
        rotateGrid(4 - directionValue);
    }
    public boolean winGame(){
        boolean win = false;
        for (int i = 0; i < myGrid.length; i++) {
            if (myGrid[i].getValue() == 2048) {
                win = true;
            }
        }
        return win;
    }
    public boolean looseGame(){
        boolean loose = false;
        if (availableSpace().isEmpty() && !Arrays.equals(myGrid, lastGrid)) {
            loose = true;
        }
        return loose;
    }
    public void setLastGrid(){
        this.lastGrid = myGrid.clone();
    }
    public int getCol(){
     return this.col;   
    }
    public int getRow(){
        return this.row;
    }
    public int getTiles(){
        return this.col * this.row;
    }
    public Tile[] getGrid(){
        return this.myGrid;
    }
}
