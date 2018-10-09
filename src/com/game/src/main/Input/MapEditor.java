package Input;

import dif.Game;
import Objects.MapObject;
import dif.QuadTree;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;


public class MapEditor {

    private static LinkedList<MapObject> l = new LinkedList<MapObject>();
    private static QuadTree qTree = new QuadTree(0,0, Game.WIDTH, Game.HEIGHT);
    private static MapObject temp;
    public static int x = 10;
    public static int y = 10;

    public static void add(MouseEvent e){
        // rundet auf das nächste vielfache von 25
        // warnung muss leider ignoriert werden
        l.add(new MapObject(e.getX() / 10 * 10,e.getY() / 10 * 10,x,y));
        qTree.add(new MapObject(e.getX() / 10 * 10,e.getY() / 10 * 10,x,y));

    }

    public static void setTemp(MouseEvent e){
        // rundet auf das nächste vielfache von 25
        // warnung muss leider ignoriert werden
        temp = new MapObject(e.getX()/25*25,e.getY()/25*25,x,y);
    }

    public static void render(Graphics g){

        if(Game.debug.showQTree)
            qTree.show(g);

        // error wenn gleichzeitig durch die liste iteriert wird und etwas hinzugefügt wird
        try{
            for(MapObject m: l){
                m.render(g);
            }
            if(temp != null){
                temp.render(g);
            }
        }catch(ConcurrentModificationException e){
            e.printStackTrace();
        }
    }
}