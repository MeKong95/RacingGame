package Objects;

import dif.Game;
import dif.SpriteSheet;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapObject {
    // size and position of objects
    private final double xpos;
    private final double ypos;
    private final double xlen;
    private final double ylen;
    private BufferedImage mapobj;

    // constructor for size, position and sprite
    public MapObject(double x, double y, double width, double height){
        this.xpos = x;
        this.ypos = y;
        this.xlen = width;
        this.ylen = height;
        SpriteSheet ss = new SpriteSheet(Game.getmpobj());
        if(xlen == ylen)
            mapobj = ss.grabImage(1, 1, 32, 32);
        else if(xlen > ylen)
            mapobj = ss.grabImage(1, 8, 32*8, 32);
        else if(xlen < ylen)
            mapobj = ss.grabImage(8, 1, 32, 32*8);
    }

    //für later add ons
    /*public void setXpos(double x){
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
    }*/
    public double getXpos(){
        return xpos;
    }
    public double getYpos(){
        return ypos;
    }
    public double getXlen(){return xlen;}
    public double getYlen(){return ylen;}
    public void render(Graphics g){
        g.drawImage(mapobj, (int)xpos, (int)ypos,(int)xlen, (int)ylen, null);
    }

    public void renderDebug(Graphics g){
        g.setColor(Color.RED);
        g.drawRect((int)xpos,(int)ypos,(int)xlen,(int)ylen);
    }
}
