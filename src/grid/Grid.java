/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grid;

import tile.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Flo
 */
public class Grid {
    final Tile[] myGrid;
    final Tile[] lastGrid;
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
        for (Tile LineTile1 : LineTile) {
            myGrid[getArrayIndex(LineTile1)].setValue(LineTile1.getValue());
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
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < LineTile.length - 1; i++) {
                if (LineTile[i].isNull()) {
                    LineTile[i].setValue(LineTile[i + 1].getValue());
                    LineTile[i + 1].setValue(0);
                }
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
                        tmp = this.myGrid[getArrayIndex(i+1,j+1)].getValue();
                        this.myGrid[getArrayIndex(i+1,j+1)].setValue(this.myGrid[getArrayIndex(j+1,n-i)].getValue());
                        this.myGrid[getArrayIndex(j+1,n-i)].setValue(this.myGrid[getArrayIndex(n-i,n-j)].getValue());
                        this.myGrid[getArrayIndex(n-i,n-j)].setValue(this.myGrid[getArrayIndex(n-j,i+1)].getValue());
                        this.myGrid[getArrayIndex(n-j,i+1)].setValue(tmp);
                }
            }
        }
    }
    protected void rotateGrid(int numberOfRotation, Tile[] myGrid){
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
    private boolean availableSpace(Tile[] mGrid) {
        ArrayList<Integer> availableSpace = new ArrayList<>();
        for (int i = 0; i < mGrid.length; i++) {
            if (mGrid[i].isNull()) {
                availableSpace.add(i);
            }
        }
        return availableSpace.isEmpty();
    }
    public void addTile(){
        myGrid[(int)availableSpace().get(0)].setValue(2);
    }   
    private void moveGrid(){
        for (int i = 1; i <= this.row; i++) {
            mergeLine(myGrid, removeSpace(mergeTile(removeSpace(extractLine(myGrid, i)))));
        }
    }
    private void moveGrid(Tile[] mGrid){
        for (int i = 1; i <= this.row; i++) {
            mergeLine(mGrid, removeSpace(mergeTile(removeSpace(extractLine(mGrid, i)))));
        }
    }
    public void direction(int directionValue){
        setLastGrid();
        rotateGrid(directionValue);
        moveGrid();
        rotateGrid(4 - directionValue);
    }
    private Tile[] direction(int directionValue, Tile[] mGrid){
        rotateGrid(directionValue, mGrid);
        moveGrid(mGrid);
        rotateGrid(4 - directionValue);
        return mGrid;
    }
    public boolean winGame(){
        boolean win = false;
        for (Tile myGrid1 : myGrid) {
            if (myGrid1.getValue() == 2048) {
                win = true;
            }
        }
        return win;
    }
    public boolean looseGame(){
        boolean loose = false;
        if (availableSpace().isEmpty() && !canMove() && tryMerge(shadowGrid())) {
            loose = true;
        }
        return loose;
    }
    public void setLastGrid(){
        for (int i = 0; i < myGrid.length; i++) {
            lastGrid[i].setValue(myGrid[i].getValue());
        }
    }
    public Tile[] shadowGrid(){
        Tile[] newGrid = initialiseGrid();
        for (int i = 0; i < myGrid.length; i++) {
            newGrid[i].setValue(myGrid[i].getValue());
        }
        return newGrid;
    }
    public boolean canMove(){
        int same = 0;
        for (int i = 0; i < myGrid.length; i++) {
            if (myGrid[i].getValue() == lastGrid[i].getValue())
                same++;
        }
        return same < 16;
   }
    private boolean tryMerge(Tile[] mGrid){
        int possible = 0;
        for (int i = 0; i < 4; i++) {
            if (!availableSpace(direction(i,mGrid))) {
                possible++;
            }
        }
        return possible == 0;
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
    public Tile getTile(int number){
        return this.myGrid[number];
    }
    public void showLog(){
        System.out.println("\n");
        for (int i = 1; i <= 16; i++) {
            if((i%4) != 0)   
            System.out.print(Integer.toString(myGrid[i - 1].getValue()));
            else 
            System.out.println(Integer.toString(myGrid[i - 1].getValue()));
        }
        System.out.println("\n");
        for (int i = 1; i <= 16; i++) {
            if((i%4) != 0)   
            System.out.print(Integer.toString(lastGrid[i - 1].getValue()));
            else 
            System.out.println(Integer.toString(lastGrid[i - 1].getValue()));
        }
    }
}
