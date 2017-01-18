/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;
import grid.*;
import java.awt.GridLayout;
/**
 *
 * @author Flo
 */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
public class Game extends JPanel {
    public JFrame GL = new JFrame();
    private Grid myGrid = new Grid();
    public final static int LEFT = 0;
    public final static int UP = 1;
    public final static int RIGHT = 2;
    public final static int DOWN = 3;
    public final static int CONTINUE = 0;    
    public final static int WIN = 2;    
    public final static int LOOSE = 1;    
    
    public Game(){     
        settings(GL);
        myGrid.addTile();
        myGrid.addTile();
        launchGame();
        fillGrid();
        GL.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void settings(JFrame f){
        f.setVisible(true);
        f.setFocusable(true);
        f.setTitle("2048");
        f.setSize(600, 600);
        f.setLayout(new GridLayout(4,4));
    }
    private void fillGrid(){
        for (int i = 0; i < 16; i++) {
            GL.add(new JLabel((myGrid.getTile(i).toString())));
        }
    }
    private void launchGame(){
        addKeyListener(new KeyAdapter() {
            private Grid myGrid;
            @Override
            public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
              resetGame();
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                  moveEvent(LEFT);
                  break;
                case KeyEvent.VK_DOWN:
                moveEvent(DOWN);
                break;
                case KeyEvent.VK_RIGHT:
                  moveEvent(RIGHT);
                  break;
                case KeyEvent.VK_UP:
                  moveEvent(UP);
                  break;
              }
            }
        });
    }    
    private int checkGame(){
        return myGrid.winGame() ? WIN : myGrid.looseGame() ? LOOSE : CONTINUE;
    }
    public void moveEvent(int direction){
        myGrid.direction(direction);
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
        fillGrid();
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
    }
}