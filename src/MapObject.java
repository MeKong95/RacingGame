import java.awt.*;
import java.awt.image.BufferedImage;

public class MapObject {
    // dimensionen und position des objekts
    private double xpos;
    private double ypos;
    private double xlen;
    private double ylen;

    private BufferedImage mapobj;

    // konstruktor legt dimension, position und sprite fest
    public MapObject(double x, double y, double width, double height, Game gameobj){

        this.xpos = x;
        this.ypos = y;
        this.xlen = width;
        this.ylen = height;

        SpriteSheet ss = new SpriteSheet(gameobj.getSpriteSheet());

        mapobj = ss.grabImage(1, 2, 32, 32);
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

        g.drawImage(mapobj, (int)xpos, (int)ypos,(int)xlen, (int)ylen, null);
    }
}
