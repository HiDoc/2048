/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tile;

/**
 *
 * @author Flo
 */
public class Tile {
    private int value;
    private int ord;
    private int abs;
    
    public Tile(){
        this.value = 0;
    }
    public Tile(int ord, int abs){
        this.value = 0;
        this.abs = abs;
        this.ord = ord;
    }
    public int getValue(){
        return this.value;
    }
    public void setValue(int value){
        this.value = value;
    }
    public int getAbs(){
        return this.abs;
    }
    public void setAbs(int abs){
        this.abs = abs;
    }
    public int getOrd(){
        return this.ord;
    }
    public void setOrd(int ord){
        this.ord = ord;
    }
    public boolean isNull(){
        return this.value == 0;
    }
    public boolean sameValue(Tile compareTile){
        return this.value == compareTile.getValue();
    }
}
