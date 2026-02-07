package balls;

import java.util.Random;

public class BallGrid {
    public int[][] grid;
    int maxCol, maxRow;

    public BallGrid(int maxCol, int maxRow) {
        this.maxCol = maxCol;
        this.maxRow = maxRow;
        grid = new int[maxCol][maxRow];
        initGrid();
    }

    public void initGrid() {
        //CLEARING GRID
        for (int c = 0; c < maxCol; c++) {
            for (int r = 0; r < maxRow; r++) {
                grid[c][r] = -1;
            }
        }
        /* PLACING BALLS
         * paining at grid column 2-6 row 4-8
         * */
        for (int c = 1; c < 7; c++) {
            for (int r = 3; r < 8; r++) {
                grid[c][r] = c;
            }
        }
    }

    //SHUFFLING GRID
    /* e.g.,
     * temp = red;
     * red = blue;
     * blue = temp;
     */
    public void shuffle() {
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            int c1 = rand.nextInt(6) + 1;
            int r1 = rand.nextInt(5) + 3;

            int c2 = rand.nextInt(6) + 1;
            int r2 = rand.nextInt(5) + 3;


            int temp = grid[c1][r1];
            grid[c1][r1] = grid[c2][r2];
            grid[c2][r2] = temp;
        }
    }
}
