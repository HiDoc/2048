/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Flo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    JFrame game = new JFrame();
    game.setTitle("2048 Game");
    game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    game.setSize(500, 500);
    game.setResizable(false);

    game.add(new Game());

    game.setLocationRelativeTo(null);
    game.setVisible(true);
  }
}
   
