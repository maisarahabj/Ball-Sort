package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    GamePanel gp;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();

        if (mx >= 7 * gp.tileSize && mx <= 8 * gp.tileSize && my >= 16 && my <= 16 + gp.tileSize) {
            gp.ballGrid.initGrid();
            gp.stopwatch.restart();
            gp.gameReady = false;
            gp.heldBallColor = -1;
            gp.moves = 0;
            gp.repaint();
            return;
        }

        int col = mx/gp.tileSize;
        gp.handleMouseClick(col);
    }


}
