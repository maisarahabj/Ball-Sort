package main;

public class Stopwatch {

    long startTime;
    boolean started = false;

    public String hours_string = "00";
    public String minutes_string = "00";
    public String seconds_string = "00";

    public void start() {

        startTime = System.currentTimeMillis();
        started = true;
    }

    public void stop() {
        started = false;
    }

    public void restart() {
        started = false;
        hours_string = "00";
        minutes_string = "00";
        seconds_string = "00";
    }


    public void update() {
        if (started) {
            long currentTime = System.currentTimeMillis() - startTime;

            int h = (int) (currentTime / 3600000);
            int m = (int) (currentTime / 60000) % 60;
            int s = (int) (currentTime / 1000) % 60;


            hours_string = String.format("%02d", h);
            minutes_string = String.format("%02d", m);
            seconds_string = String.format("%02d", s);
        }
    }
}
