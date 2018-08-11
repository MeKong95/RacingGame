package com.game.src.main.Objects;

import com.game.src.main.Game;
import com.game.src.main.QuadTree;
import com.game.src.main.SpriteSheet;
import com.game.src.main.Timer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Map;

import com.game.src.main.Menu.Race;

public class Player {
    private String name;
    private final double s = 3;
    private double x;
    private double y;
    private double velX = 0;
    private double velY = 0;
    private double velA = 0;
    private double accX = 0;
    private double accY = 0;
    private int size = 32;  //groeße des sprites , wird fuer kollision benoetigt
    private double angle;
    private LinkedList<MapObject> list = new LinkedList<MapObject>();

    private BufferedImage s0, s1, s2,s3,s4,s5,s6,s7; // variable für sprites des spielers
    private Timer timer;

    public Player(double x, double y, double winkel, String name){
        //konstruktor legt startposition, winkel und sprite fest
        this.x = x;
        this.y = y;
        this.angle = winkel;
        this.name = name;
        SpriteSheet ss = new SpriteSheet(Game.getSpriteSheet());
        s0 = ss.grabImage(2, 1, 32, 32);
        s1 = ss.grabImage(3, 1, 32, 32);
        s2 = ss.grabImage(3, 2, 32, 32);
        s3 = ss.grabImage(3, 3, 32, 32);
        s4 = ss.grabImage(2, 3, 32, 32);
        s5 = ss.grabImage(1, 3, 32, 32);
        s6 = ss.grabImage(1, 2, 32, 32);
        s7 = ss.grabImage(1, 1, 32, 32);
        timer = new Timer(this);
    }

    /* ungenutzte getter und setter funktionen
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setVelX(double velX){
        this.velX = velX;
    }
    public void setVelY(double velY){
        this.velY = velY;
    }
    public double getVelX(){
        return velX;
    }
    public double getVelY(){
        return velY;
    }
    */
    public String getName(){
        return name;
    }

    public Timer getTimer(){ return timer;}

    public void setAccX(double accX){
        this.accX = accX;
    }
    public void setAccY(double accY){
        this.accY = accY;
    }

    private boolean Collision(MapObject m){ //Entscheidet ob der Spieler mit einer Wand kollidiert
        return !(
                x > m.getXpos() + m.getXlen() ||
                x + size < m.getXpos() ||
                y > m.getYpos() + m.getYlen() ||
                y + size < m.getYpos());
                //es ist wesentlich effizienter zu testen ob sie nicht kollidieren und dann zu negieren
    }

    public void tick(QuadTree qtree) {
        // startet timer bei der ersten bewegung
        if(velX!=0 || velY!=0){
            timer.start();
        }

        // auto verliert langsam geschwindigkeit
        velX*=0.97;
        velY*=0.97;

        // verallgemeinerung der geschwindigkeiten nahe null
        // bewirkt schönere drehung vom auto
        if(velX < 0.01 && velX > -0.01)
            velX = 0;
        if(velY < 0.01 && velY > -0.01)
            velY = 0;

        velX+=accX;
        velY+=accY;
        velA=Math.sqrt(Math.pow(velX,2)+Math.pow(velY,2));

        if(velA > s) {
            velX -= (velA - s) * (velX / velA);
            velY -= (velA - s) * (velY / velA);
        }//Geschwindigkeitsbegrenzung in x,y,x/y-Richtung

        //Geschwindigkeitsanzeige entwicklung
        //System.out.println(Math.sqrt(Math.pow(velX,2)+Math.pow(velY,2)));


        // bewegung in x richtung, falls es eine kollision gibt, zurueck bewegen
        x+=velX;
        list = qtree.retrieve((int)x,(int)y,size);
        for (Object aList : list) {
            if (Collision((MapObject) aList)) {
                x -= velX;
                break;
            }
        }
        // bewegung in y richtung, falls es eine kollision gibt, zurueck bewegen
        y+=velY;
        list = qtree.retrieve((int)x,(int)y,size);
        for (Object aList : list) {
            if (Collision((MapObject) aList)) {
                y -= velY;
                break;
            }
        }

        //hard coded begrenzung ans spielfenster
        if(x < 0)
            x = 0;
        if(x > Game.WIDTH-size)
            x = Game.WIDTH-size;
        if(y < 0)
            y = 0;
        if(y > Game.HEIGHT-size)
            y = Game.HEIGHT-size;

    }

    public void tick(GoalObject g){
        // test zur kollision mit dem ziel
        if(     x+size >= g.getXpos() &&
                x <= g.getXpos() + g.getXlen() &&
                y+size >= g.getYpos() &&
                y <= g.getYpos() + g.getYlen()
                )
            timer.stop();
    }

    public void render(Graphics g){
        /*if(velX < 0.01)
            damit division durch 0 kein problem bringt
            winkel = (360/(2*Math.PI)* Math.atan2(0.01, velY ));

        else*/
        //atan2 scheint kein problem mit velX = 0 zu haben
        //nur winkel aktualisieren wenn man sich auch wirklich signifikant bewegt
        if(Math.abs(velX) > 0.05 || Math.abs(velY) > 0.05)
            angle = Math.toDegrees(Math.atan2(velX, velY));

        if(angle > 180-45/2 || angle < -180 +45/2)
            g.drawImage(s0, (int)x, (int)y, null);
        else if(angle >= 135-45/2 && angle <= 135+45/2)
            g.drawImage(s1, (int)x, (int)y, null);
        else if(angle > 90-45/2 && angle < 90+45/2)
            g.drawImage(s2, (int)x, (int)y, null);
        else if(angle >= 45-45/2 && angle <= 45+45/2)
            g.drawImage(s3, (int)x, (int)y, null);
        else if(angle > 0-45/2 && angle < 0+45/2)
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

