package Objects;

import dif.Game;
import dif.QuadTree;
import dif.SpriteSheet;
import dif.Timer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player {
    private final String name;
    private double x;
    private double y;
    private double velX = 0;
    private double velY = 0;
    private double accX = 0;
    private double accY = 0;
    private final int size = 32;  //size of sprite,used for collision
    private double angle;
    private final BufferedImage s0;
    private final BufferedImage s1;
    private final BufferedImage s2;
    private final BufferedImage s3;
    private final BufferedImage s4;
    private final BufferedImage s5;
    private final BufferedImage s6;
    private final BufferedImage s7;
    private final Timer timer;

    public Player(double x, double y, double angle, String name){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.name = name;
        SpriteSheet ob = new SpriteSheet(Game.getplobj());
        s0 = ob.grabImage(1, 1, 32, 32);
        s1 = ob.grabImage(2, 1, 32, 32);
        s2 = ob.grabImage(3, 1, 32, 32);
        s3 = ob.grabImage(4, 1, 32, 32);
        s4 = ob.grabImage(4, 2, 32, 32);
        s5 = ob.grabImage(3, 2, 32, 32);
        s6 = ob.grabImage(2, 2, 32, 32);
        s7 = ob.grabImage(1, 2, 32, 32);
        timer = new Timer(this);
    }

    public String getName(){
        return name;
    }
    public void setAccX(double accX){
        this.accX = accX;
    }
    public void setAccY(double accY){
        this.accY = accY;
    }
    private boolean Collision(MapObject m){ //checks for collision
        return !(
                x > m.getXpos() + m.getXlen() ||
                x + size < m.getXpos() ||
                y > m.getYpos() + m.getYlen() ||
                y + size < m.getYpos());
                //higher efficiency when testing for "no-collision" and negate this
    }

    public void tick(QuadTree qtree) {//Starting timer with first movement
        if(velX!=0 || velY!=0){
            timer.start();
        }
        // player decelerates without further input
        velX*=0.97;
        velY*=0.97;
        if(velX < 0.01 && velX > -0.01)
            velX = 0;
        if(velY < 0.01 && velY > -0.01)
            velY = 0;
        velX+=accX;
        velY+=accY;
        double velA = Math.sqrt(Math.pow(velX, 2) + Math.pow(velY, 2));
        double s = 3;
        if(velA > s) {
            velX -= (velA - s) * (velX / velA);
            velY -= (velA - s) * (velY / velA);
        }//speed-restriction in x,y,x/y-direction
        x+=velX;
        LinkedList<MapObject> list = qtree.retrieve((int) x, (int) y, size);
        for (Object aList : list) {
            if (Collision((MapObject) aList)) {
                x -= velX;
                break;
            }
        }
        y+=velY;
        list = qtree.retrieve((int)x,(int)y,size);
        for (Object aList : list) {
            if (Collision((MapObject) aList)) {
                y -= velY;
                break;
            }
        }
        if(x < 0)
            x = 0;
        if(x > Game.WIDTH-size)
            x = Game.WIDTH-size;
        if(y < 0)
            y = 0;
        if(y > Game.HEIGHT-size)
            y = Game.HEIGHT-size; //boundary in frame
    }

    public void tick(GoalObject g){
        // collision with "finish" object
        if(!(
                x > g.getXpos() + g.getXlen() ||
                x + size < g.getXpos() ||
                y > g.getYpos() + g.getYlen() ||
                y + size < g.getYpos()))
            timer.stop();
    }

    public void render(Graphics g){
        if(Math.abs(velX) > 0.05 || Math.abs(velY) > 0.05)
            angle = Math.toDegrees(Math.atan2(velX, velY));//angle of movement

        if(angle > 180-45/2 || angle < -180 +45/2)
            g.drawImage(s0, (int)x, (int)y, null);
        else if(angle >= 135-45/2 && angle <= 135+45/2)
            g.drawImage(s1, (int)x, (int)y, null);
        else if(angle > 90-45/2 && angle < 90+45/2)
            g.drawImage(s2, (int)x, (int)y, null);
        else if(angle >= 45-45/2 && angle <= 45+45/2)
            g.drawImage(s3, (int)x, (int)y, null);
        else if(angle > 0-45/2 && angle < 45 / 2)
            g.drawImage(s4, (int)x, (int)y, null);
        else if(angle >= -45-45/2 && angle <= -45+45/2)
            g.drawImage(s5, (int)x, (int)y, null);
        else if(angle > -90-45/2 && angle < -90+45/2)
            g.drawImage(s6, (int)x, (int)y, null);
        else if(angle >= -135-45/2 && angle <= -135+45/2)
            g.drawImage(s7, (int)x, (int)y, null);
        timer.displayTime(g);
    }
}