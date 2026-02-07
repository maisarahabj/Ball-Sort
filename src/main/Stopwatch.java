package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Stopwatch implements ActionListener {

    JButton resetButton = new JButton("RESET");
    JLabel timeLabel = new JLabel();
    GamePanel gp;

    int elapsedTime = 0;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    long startTime;
    long currentTime;
    boolean started = false;

    String seconds_string = String.format("%02d", seconds);
    String minutes_string = String.format("%02d", minutes);
    String hours_string = String.format("%02d", hours);

    Timer timer = new Timer(1000, new ActionListener() {

        public void actionPerformed(ActionEvent e) {

            elapsedTime = elapsedTime + 1000;
            hours = (elapsedTime / 3600000);
            minutes = (elapsedTime / 60000) % 60;
            seconds = (elapsedTime / 1000) % 60;
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);

        }

    });


    Stopwatch(GamePanel gp) {
        timeLabel.setText(hours_string + " : " + minutes_string + " : " + seconds_string);
        this.gp = gp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gp.gameReady) {
            started = true;
            start();
        }

        if (!gp.gameReady) {
            started = false;
            stop();
        }

        // need a logic for reset? if gp.Reset statement?


    }

    void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    void restart() {
        timer.stop();
        elapsedTime = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        hours_string = String.format("%02d", hours);
        timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);

    }

    public void update() {
        if (started) {
            currentTime = System.currentTimeMillis() - startTime;

            // Convert milliseconds into readable numbers
            int h = (int) (currentTime / 3600000);
            int m = (int) (currentTime / 60000) % 60;
            int s = (int) (currentTime / 1000) % 60;

        }
    }
}
