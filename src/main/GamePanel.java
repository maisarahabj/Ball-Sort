package main;

import balls.Ball;
import balls.BallGrid;
import balls.BallLoader;

import javax.swing.JPanel;
import java.awt.*;
import java.io.InputStream;

import static java.awt.Font.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTiles = 16;
    final int scale = 3;
    public final int tileSize = originalTiles * scale; // 64x64 pixels
    public final int maxScreenCol = 9;
    public final int maxScreenRow = 10;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    //BALL-GRID SETTINGS
    public final int stackHeight = 5;
    public final int stackBottom = 7;
    public final int ceiling = stackBottom - stackHeight;

    // COMPONENTS
    Thread gameThread;
    MouseHandler mouseH = new MouseHandler(this);
    Stopwatch stopwatch = new Stopwatch();
    Font customFont;

    // CUSTOM CLASSES
    public Ball[] ballTypes;
    public BallGrid ballGrid;

    // GAME TRACKER
    boolean gameReady = false;
    int moves = 0;
    int heldBallColor = -1;
    int heldFromCol = -1;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(46, 46, 46));
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseH);

        ballTypes = BallLoader.loadBallTypes();
        ballGrid = new BallGrid(maxScreenCol, maxScreenRow);

        loadFont();
    }

    public void loadFont() {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Pixeltype.ttf");
            customFont = createFont(TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

        } catch (Exception e) {
            e.printStackTrace();
            customFont = new Font("Arial", PLAIN, 20);
        }
    }

    public void handleMouseClick(int col) {
        //INITIAL SHUFFLE
        if (!gameReady) {
            ballGrid.shuffle();
            gameReady = true;
            stopwatch.start();
            moves = 0;
        }
        //PICK/DROP BALL
        else {
            if (heldBallColor == -1) {
                pickUpBall(col);
            } else {
                dropBall(col);
            }
        }
        repaint();
    }

    public void pickUpBall(int col) {
        for (int r = 0; r < maxScreenRow; r++) {

            if (ballGrid.grid[col][r] != -1) {
                heldBallColor = ballGrid.grid[col][r];
                heldFromCol = col;
                ballGrid.grid[col][r] = -1;
                break;
            }
        }
    }

    public void dropBall(int col) {
        if (heldBallColor == -1 || col < 1 || col > 7) return;
        if (ballGrid.grid[col][ceiling + 1] != -1) return;

        for (int r = stackBottom; r > ceiling; r--) {
            if (ballGrid.grid[col][r] == -1) {
                ballGrid.grid[col][r] = heldBallColor;
                heldBallColor = -1;
                moves++;
                break;
            }
        }
    }

    public boolean gameEnd() {
        for (int c = 1; c <= 7; c++) {
            int bottomBallColor = ballGrid.grid[c][stackBottom];
            if (bottomBallColor == -1) { continue; }

            for (int r = 3; r <= 7; r++) {
                if (ballGrid.grid[c][r] != bottomBallColor){
                    return false;
                }
            }
        }
        stopwatch.stop();
        return true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //FONT
        if (customFont != null) {
            g2.setFont(customFont.deriveFont(45f));
        }

        //RESET BUTTON
        g2.setColor(Color.WHITE);
        int resetX = 7 * tileSize;
        int resetY = 16;
        g2.drawRect(resetX, resetY, tileSize, tileSize);
        g2.drawString("R", resetX + 15, resetY + 33);

        // DRAW TIMER
        g2.drawString(stopwatch.hours_string + ":" + stopwatch.minutes_string + ":" + stopwatch.seconds_string, tileSize, 50);

        //MOVE COUNT
        g2.drawString("MOVES: " + moves, tileSize, 80);

        //BOARDER GRID SQUARES
        g2.setColor(new Color(112, 112, 112));
        for (int c = 1; c <= 7; c++) {
            for (int r = 2; r <= 7; r++) {
                g2.drawRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }


        //BALLS
        for (int c = 0; c < maxScreenCol; c++) {
            for (int r = 0; r < maxScreenRow; r++) {
                int colorID = ballGrid.grid[c][r];

                if (colorID != -1) {
                    g2.drawImage(ballTypes[colorID].image, c * tileSize, r * tileSize, tileSize, tileSize, null);
                }
            }
        }

        if (heldBallColor != -1) {
            int xPos = (heldFromCol * tileSize);
            int yPos = (2 * tileSize);
            g2.drawImage(ballTypes[heldBallColor].image, xPos, yPos, tileSize, tileSize, null);
        }

        g2.dispose();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            stopwatch.update();
            gameEnd();
            repaint();
            try {
                Thread.sleep(16);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}