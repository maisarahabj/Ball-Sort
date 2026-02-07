/*
 *   Ball[]              : initialize array to hold the Ball obj
 *   ImageIO.read        : load sprite sheet image into memory
 *   for loops           : go through sprite sheet row by row
 *   getSubimage         : get the individual sub sheet
 *   types[index] = ...  : instantiate the Ball and assign properties
 *   return              : returns array
 * */


package balls;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class BallLoader {

    public static Ball[] loadBallTypes() {
        Ball[] types = new Ball[9];

        try {
            //LOAD SPRITE SHEET INTO VARIABLE sheet
            BufferedImage sheet = ImageIO.read(Objects.requireNonNull(BallLoader.class.getResourceAsStream("/ball/balls.png")));

            //SCANS THE SHEET INTO 3x3 GRID
            int index = 0;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    types[index] = new Ball();

                    //SLICING INTO 3x3 GRID EACH GRID BEING 8px
                    //sheet.getSubimage(x, y, w, h)
                    types[index].image = sheet.getSubimage(col * 8, row * 8, 8, 8);
                    types[index].colorID = index;
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return types;
    }

}
