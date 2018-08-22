package com.game.src.main.Input;

import com.game.src.main.Objects.MapObject;

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
    private static MapObject temp;
    public static int x = 400;
    public static int y = 50;

    public static void add(MouseEvent e){
        // rundet auf das nächste vielfache von 25
        l.add(new MapObject(e.getX() / 25 * 25,e.getY() / 25 * 25,x,y));
    }

    public static void setTemp(MouseEvent e){
        // rundet auf das nächste vielfache von 25
        temp = new MapObject(e.getX()/25*25,e.getY()/25*25,x,y);
    }

    public static void render(Graphics g){
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

    public static void end(int track){
        File f = new File("res/map_" + track + ".crsp");
        try{
            FileWriter fw = new FileWriter(f, true); // true for appending
            PrintWriter pw = new PrintWriter(fw, true); // true for auto-flush
            for(MapObject m: l){
                pw.println(m.getXpos() + ";" + m.getYpos() + ";" + m.getXlen() + ";" + m.getYlen());
            }

            pw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
