/*
* Start           :   Main.java calls startGameThread().
* Logic           :   BallGrid sets up its 2D array with numbers (e.g., Column 1, Row 4 is colorID = 1).
* Drawing         :   Java automatically calls paintComponent.
* Looping         :   When the loop hits c=1, r=4, it sees colorID = 1.
* Stamping        :   It draws the ball image at x=64,y=256 (if tileSize is 64).
* Looping Again   :   The run() method waits 16ms and then stays ready for the next frame or click.*
 */

package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Ball Sort Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }

}
