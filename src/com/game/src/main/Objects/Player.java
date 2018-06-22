package com.game.src.main.Objects;

import com.game.src.main.Game;
import com.game.src.main.SpriteSheet;
import com.game.src.main.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

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
    private int size = 20;  //groeße des sprites , wird fuer kollision benoetigt
    private double winkel = 0;

    private BufferedImage sprite0, sprite1, sprite2,sprite3,sprite4,sprite5,sprite6,sprite7; // variable für sprites des spielers
    private Timer timer;

    public Player(double x, double y, double winkel, String name){
        //konstruktor legt startposition, winkel und sprite fest
        this.x = x;
        this.y = y;
        this.winkel = winkel;
        this.name = name;
        SpriteSheet ss = new SpriteSheet(Game.getSpriteSheet());

        sprite0 = ss.grabImage(1, 1, 32, 32);
        sprite1 = ss.grabImage(2, 1, 32, 32);
        sprite2 = ss.grabImage(3, 1, 32, 32);
        sprite3 = ss.grabImage(4, 1, 32, 32);
        sprite4 = ss.grabImage(5, 1, 32, 32);
        sprite5 = ss.grabImage(6, 1, 32, 32);
        sprite6 = ss.grabImage(7, 1, 32, 32);
        sprite7 = ss.grabImage(8, 1, 32, 32);

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

    public void setAccX(double accX){
        this.accX = accX;
    }
    public void setAccY(double accY){
        this.accY = accY;
    }

    public boolean Collision(MapObject m){ //Entscheidet ob der Spieler mit einer Wand kollidiert
        return
                (x < m.getXpos()+m.getXlen()) && (x > m.getXpos()-size) //X-Seiten checken
                &&
                (y < m.getYpos()+m.getYlen()) && (y > m.getYpos()-size);//Y-Seiten checken
    }

    public void tick(LinkedList list) {


        if(velX!=0 || velY!=0){
            timer.start();
        }

        velX*=0.97;
        velY*=0.97;
        if(velX < 0.01 && velX > -0.01)
            velX = 0;
        if(velY < 0.01 && velY > -0.01)
            velY = 0;


        //Geschwindigkeitsbegrenzung x-Richtung
        velX+=accX;

        /* Diese art der begrenzung ist nicht mehr nötig da die untere lösung das bereits handlet
        if(velX>s)
            velX = s;
        if(velX<-s)
            velX = -s;*/
        //Geschwindigkeitsbegrenzung y-Richtung
        velY+=accY;

        /*Diese art der begrenzung ist nicht mehr nötig da die untere lösung das bereits handlet
        if(velY>s)
            velY = s;
        if(velY<-s)
            velY = -s;*/

        //Korrektur damit Diagonal Bewegung nicht schneller als gerade Bug

        //Argument kann spaeter fuer Geschwindigkeits Anzeige benutzt werden
        velA=Math.sqrt(Math.pow(velX,2)+Math.pow(velY,2));

        //alte lösung
        /*if(velA>s) {
            if(velX>Math.sqrt(4.5)){
                velX = Math.sqrt(4.5);
            }
            if(velX<-Math.sqrt(4.5)){
                velX = -Math.sqrt(4.5);
            }
            if(velY>Math.sqrt(4.5)){
                velY = Math.sqrt(4.5);
            }
            if(velY<-Math.sqrt(4.5)){
                velY = -Math.sqrt(4.5);
            }
        }*/

        //neue loesung ist natuerlicher

        if(velA > s) {
            velX -= (velA - s) * (velX / velA);
            velY -= (velA - s) * (velY / velA);
        }

        //Geschwindigkeitsanzeige entwicklung
        System.out.println(Math.sqrt(Math.pow(velX,2)+Math.pow(velY,2)));





        // das aufteilen in x und y bewegung ermoeglicht das "entlang-rutschen" an einer wand

        // bewegung in x richtung, falls es eine kollision gibt, zurueck bewegen
        x+=velX;
        for(int i = 0; i < list.size(); i++) {
            if (Collision((MapObject) list.get(i))) {
                x -= velX;
                break;
            }
        }
        // bewegung in y richtung, falls es eine kollision gibt, zurueck bewegen
        y+=velY;
        for(int i = 0; i < list.size(); i++){
            if(Collision((MapObject) list.get(i))) {
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
        if(velX > 0.05 || velX < -0.05 || velY > 0.05 || velY < -0.05)
            winkel = (360/(2*Math.PI) * Math.atan2(velX, velY));

        if(winkel > 180-45/2 || winkel < -180 +45/2)
            g.drawImage(sprite0, (int)x, (int)y, null);
        else if(winkel >= 135-45/2 && winkel <= 135+45/2)
            g.drawImage(sprite1, (int)x, (int)y, null);
        else if(winkel > 90-45/2 && winkel < 90+45/2)
            g.drawImage(sprite2, (int)x, (int)y, null);
        else if(winkel >= 45-45/2 && winkel <= 45+45/2)
            g.drawImage(sprite3, (int)x, (int)y, null);
        else if(winkel > 0-45/2 && winkel < 0+45/2)
            g.drawImage(sprite4, (int)x, (int)y, null);
        else if(winkel >= -45-45/2 && winkel <= -45+45/2)
            g.drawImage(sprite5, (int)x, (int)y, null);
        else if(winkel > -90-45/2 && winkel < -90+45/2)
            g.drawImage(sprite6, (int)x, (int)y, null);
        else if(winkel >= -135-45/2 && winkel <= -135+45/2)
            g.drawImage(sprite7, (int)x, (int)y, null);


        timer.displayTime(g);
    }


}

