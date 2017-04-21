/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;
import grid.*;
import java.awt.Color;

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
    public final static String LOOSE_MESSAGE = "Vous avez perdu !";
    public final static String WIN_MESSAGE = "Vous avez gagné !";
    public final static String ERROR_MESSAGE = "Erreur";
    
    public Game(){     
        newGame();
        settings(this.GLFrame, this.GL);
        this.GLFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    final void settings(JFrame f, JPanel p){
        f.setVisible(true);
        f.addKeyListener(this);
        f.setFocusable(true);
        f.setTitle("2048");
        f.setSize(600, 600);
        p.setLayout(new GridLayout(4,4));
        f.setContentPane(p);
    }
    private void paintGrid(){
        this.GL.removeAll();
        fillGrid();
        this.GLFrame.setContentPane(this.GL);
        this.GL.revalidate();
        this.GLFrame.setVisible(true);
        this.GL.repaint();
    }
    private void fillGrid(){
        for (int i = 0; i < 16; i++) {
            String value = "";
            if(myGrid.getTile(i).getValue() != 0)
                value = (myGrid.getTile(i).toString());
            JLabel New = new JLabel((value),JLabel.CENTER);
            New.setOpaque(true);
            New.setBackground(ColorBG((myGrid.getTile(i).getValue())));
            this.GL.add(New);
            }
    }
    
    private int checkGame(){
        return myGrid.winGame() ? WIN : myGrid.looseGame() ? LOOSE : CONTINUE;
    }
    private void moveEvent(int direction){
        myGrid.direction(direction);
        switch (checkGame()) {
            case CONTINUE:
                if (myGrid.canMove()){
                    try {
                    myGrid.addTile();
                    }
                    catch(Exception e){
                        
                    }
                    myGrid.showLog();
                }
                break;
            case LOOSE:
                JOptionPane.showMessageDialog(null,LOOSE_MESSAGE);
                resetGame();
                break;
            case WIN :
                JOptionPane.showMessageDialog(null,WIN_MESSAGE);
                resetGame();
                break;
            default:
                JOptionPane.showMessageDialog(null,ERROR_MESSAGE);
                break;
        }
        paintGrid();
    }
    
    final void newGame(){
        this.myGrid = new Grid();
        myGrid.addTile();
        myGrid.addTile();
        fillGrid();
        myGrid.showLog();
    }
    final void resetGame(){
        GLFrame.removeAll();
        GL.removeAll();
        newGame();
        paintGrid();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            newGame();
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
            default :
                break;
          } 
    }
    
     public Color ColorBG(int value) {
        switch (value) {
          case 2:    return new Color(0xeee4da);
          case 4:    return new Color(0xede0c8);
          case 8:    return new Color(0xf2b179);
          case 16:   return new Color(0xf59563);
          case 32:   return new Color(0xf67c5f);
          case 64:   return new Color(0xf65e3b);
          case 128:  return new Color(0xedcf72);
          case 256:  return new Color(0xedcc61);
          case 512:  return new Color(0xedc850);
          case 1024: return new Color(0xedc53f);
          case 2048: return new Color(0xedc22e);
        }
        return new Color(0xcdc1b4);
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