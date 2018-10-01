package dif;

import Input.FileController;
import Objects.Player;
import java.awt.*;
import java.text.DecimalFormat;

public class Timer {
    private double startTime= 0;
    private double goalTime = 0;
    private boolean running = false;
    private boolean finished = false;
    private final String name;
    private final DecimalFormat df = new DecimalFormat();
    private final Font fnt1 = new Font("arial", Font.BOLD, 30);

    public Timer(Player p){
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        name = p.getName();
    }

    public void start(){
        if(!running) {
            running = true;
            //startTime = System.currentTimeMillis();
            startTime = Game.getAllUpdates();
        }
    }

    public void displayTime(Graphics g){
        g.setFont(fnt1);
        g.setColor(Color.WHITE);
        if(!running){
            //g.drawString("0,00", Game.WIDTH/2-150, 100);
            g.drawString("Press W/A/S/D to start", Game.WIDTH / 4 + 50, Game.HEIGHT -45 );
        }else if(!finished){
            //g.drawString(df.format((System.currentTimeMillis()-startTime)/1000.0), Game.WIDTH * 1 / 4 +50, Game.HEIGHT - 45);
            g.drawString("Time: " + df.format((Game.getAllUpdates()-startTime)/60), Game.WIDTH / 4 +50, Game.HEIGHT - 45);
        }else{
            //g.drawString(df.format((goalTime)/1000.0),  Game.WIDTH * 1 / 4 +50, Game.HEIGHT - 45);
            g.drawString(df.format((goalTime)/60), Game.WIDTH / 4 +50, Game.HEIGHT - 45);
            g.drawString("Finished!", Game.WIDTH / 4 +50, Game.HEIGHT - 70);
            g.drawString("Try again (F2)", Game.WIDTH / 4 +50, Game.HEIGHT - 20);
        }
    }

    public void stop(){
        if(!finished){
            finished = true;
            int track= Game.getTrack();
            FileController f = new FileController("src/com/game/src/res/highscores_" + track + ".xml");
            //goalTime = System.currentTimeMillis() - startTime;
            goalTime = Game.getAllUpdates() - startTime;
            //f.write(String.valueOf(df.format((goalTime)/1000.0)),name);
            f.write(String.valueOf(df.format((goalTime)/60)),name);
        }
    }
}
