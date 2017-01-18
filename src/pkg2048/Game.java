/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;
import grid.*;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author Flo
 */
public final class Game extends JPanel implements KeyListener {
    public JFrame GLFrame = new JFrame();
    public JPanel GL = new JPanel();
    private Grid myGrid = new Grid();
    public final static int LEFT = 0;
    public final static int UP = 3;
    public final static int RIGHT = 2;
    public final static int DOWN = 1;
    public final static int CONTINUE = 0;    
    public final static int WIN = 2;    
    public final static int LOOSE = 1;    
    
    public Game(){     
        settings(this.GLFrame, this.GL);
        resetGame();
        this.GLFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void settings(JFrame f, JPanel p){
        f.setVisible(true);
        f.addKeyListener(this);
        f.setFocusable(true);
        f.setTitle("2048");
        f.setSize(600, 600);
        p.setLayout(new GridLayout(4,4));
        f.add(p);
    }
    private void paintGrid(){
        this.GLFrame.remove(GL);
        this.GL = new JPanel();
        this.GL.setLayout(new GridLayout(4,4));
        fillGrid();
        this.GLFrame.add(GL);
    }
    private void fillGrid(){
        for (int i = 0; i < 16; i++) {
            GL.add(new JLabel((myGrid.getTile(i).toString())));
        }
    }
    
    private int checkGame(){
        return myGrid.winGame() ? WIN : myGrid.looseGame() ? LOOSE : CONTINUE;
    }
    public void moveEvent(int direction){
        myGrid.direction(direction);
        myGrid.showLog();
        switch (checkGame()) {
            case CONTINUE:
                myGrid.setLastGrid();
                myGrid.addTile();
                break;
            case LOOSE:
                loseMessage();
                break;
            case WIN :
                winMessage();
                break;
            default:
                errorMessage();
                break;
        }
    }
    private String winMessage(){
        return "Vous avez gagné !";
    }
    private String loseMessage(){
        return "Vous avez perdu ! Appuyez sur échap pour relancer une partie";
    }
    private String errorMessage(){
        return "Erreur !";
    }
    public void resetGame(){
        this.myGrid = new Grid();
        myGrid.addTile();
        myGrid.addTile();
        fillGrid();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
              resetGame();
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    moveEvent(LEFT);
                  break;
                case KeyEvent.VK_DOWN:moveEvent(DOWN);
                    break;
                case KeyEvent.VK_RIGHT:
                    moveEvent(RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    moveEvent(UP);
                    break;
                default :
                    break;
              }
        paintGrid();
    }
    public void test (String a){
        JOptionPane.showMessageDialog(null, a);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
   
    }

}