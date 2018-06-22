package com.game.src.main;

import com.game.src.main.Input.FileController;
import com.game.src.main.Objects.Player;

import java.awt.*;
import java.text.DecimalFormat;

public class Timer {
    private double startTime= 0;
    private double goalTime = 0;
    private boolean running = false;
    private boolean finished = false;
    DecimalFormat df = new DecimalFormat();
    Font fnt1 = new Font("arial", Font.BOLD, 30);

    public Timer(Player p){
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);


    }

    public void start(){
        if(!running) {
            running = true;
            startTime = System.currentTimeMillis();
        }
    }

    public void displayTime(Graphics g){
        g.setFont(fnt1);
        g.setColor(Color.LIGHT_GRAY);
        if(!running){
            //g.drawString("0,00", Game.WIDTH/2-150, 100);
            g.drawString("Press W/A/S/D to start",Game.WIDTH/2-150, 200 );
        }else if(!finished){
            g.drawString(df.format((System.currentTimeMillis()-startTime)/1000.0), Game.WIDTH/2-150, 200);
        }else{
            g.drawString(df.format((goalTime)/1000.0), Game.WIDTH/2-150, 200);
            g.drawString("Finished!",Game.WIDTH/2-150, 250 );
            g.drawString("Try again (F2)",Game.WIDTH/2-150,300);
        }
    }

    public void stop(){
        if(!finished){
            finished = true;
            int track= Game.getTrack();
            FileController f = new FileController("res/highscores_" + track + ".crsp");
            goalTime = System.currentTimeMillis() - startTime;
            f.write(String.valueOf(df.format((goalTime)/1000.0)), "name");
        }


    }

}
