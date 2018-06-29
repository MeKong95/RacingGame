package com.game.src.main.Objects;

import com.game.src.main.Game;
import com.game.src.main.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GoalObject {
    // dimensionen und position des objekts
    private double xpos;
    private double ypos;
    private double xlen;
    private double ylen;

    private BufferedImage goalobj;

    // konstruktor legt dimension, position und sprite fest
    public GoalObject(double x, double y, double width, double height){

        this.xpos = x;
        this.ypos = y;
        this.xlen = width;
        this.ylen = height;

        SpriteSheet ss = new SpriteSheet(Game.getSpriteSheet());

        goalobj = ss.grabImage(4, 1, 32, 32);
    }

    //für mögliche erweiterungen
    /**public void setXpos(double x){
     this.xpos = x;
     }
     public void setYpos(double y){
     this.ypos = y;
     }
     public void setXlen(double x){
     this.xlen = x;
     }
     public void setYlen(double y){
     this.ylen = y;
     }**/
    public double getXpos(){
        return xpos;
    }
    public double getYpos(){
        return ypos;
    }
    public double getXlen(){return xlen;}
    public double getYlen(){return ylen;}


    public void tick(){



    }

    public  void render(Graphics g){
        g.drawImage(goalobj, (int)xpos, (int)ypos,(int)xlen, (int)ylen, null);
    }
}
