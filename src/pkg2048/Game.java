/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

/**
 *
 * @author Flo
 */
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class Game extends JPanel {
    private Grid myGrid = new Grid();
 
    public Game(){
        myGrid.addTile();
        myGrid.addTile();
        launchGame();
    }
    private void launchGame(){
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            private Grid myGrid;
            @Override
            public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
              resetGame();
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                  moveEvent(0);
                  break;
                case KeyEvent.VK_DOWN:
                moveEvent(1);
                break;
                case KeyEvent.VK_RIGHT:
                  moveEvent(2);
                  break;
                case KeyEvent.VK_UP:
                  moveEvent(3);
                  break;
              }
            }
        });
    }    
    private int checkGame(){
        return myGrid.winGame() ? 2 : myGrid.looseGame() ? 1 : 0;
    }
    public void moveEvent(int direction){
        myGrid.direction(direction);
        switch (checkGame()) {
            case 0:
                myGrid.setLastGrid();
                myGrid.addTile();
                break;
            case 1:
                loseMessage();
                break;
            case 2 :
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
    }
    /* Background color */
    private static final Color BG_COLOR = new Color(0xbbada0);

    /* Font */
    private static final Font STR_FONT = new Font(Font.SANS_SERIF,
                                                    Font.BOLD, 17);

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.setFont(STR_FONT);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        for (int i = 0; i < myGrid.getTiles() - 1; i++) {
                drawTile(g, myGrid.getGrid()[i],myGrid.getGrid()[i].getAbs() , myGrid.getGrid()[i].getOrd());
        }
    }

    /* Side of the tile square */
    private static final int SIDE = 64;

    /* Margin between tiles */
    private static final int MARGIN = 16;

    /**
     * Draw a tile use specific number and color in (x, y) coords, x and y need
     * offset a bit.
     */
    private void drawTile(Graphics g, Tile tile, int x, int y) {
        int val = tile.getValue();
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.fillRect(xOffset, yOffset, SIDE, SIDE);    
    }

    private static int offsetCoors(int arg) {
        return arg * (MARGIN + SIDE) + MARGIN;
    }

}
