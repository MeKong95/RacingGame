package com.game.src.main;

// Algorithmus verringert anzahl der kollisionsberechnungen O(x) --> O(ln(x))
// https://de.wikipedia.org/wiki/Quadtree

import com.game.src.main.Objects.MapObject;

import java.awt.*;
import java.util.LinkedList;

public class QuadTree {

    private static int count = 0;
    private int x, y, width, height;
    private QuadTree lu, ld, ru, rd; //rekursive bäume left up, left down, right up, right down
    private LinkedList<MapObject> containedObj = new LinkedList<MapObject>(); //Alle objekte die direkt im qtree enthalten sind, nicht in den children


    public QuadTree(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public LinkedList<MapObject> retrieve(int x, int y, int size){
        LinkedList<MapObject> retrieved = new LinkedList<MapObject>();
        //sucht alle potenziell kollidierenden rechtecke
        if(intersects(x,y,size)){
            //wenn spieler das rechteck schneidet, testen welche kinder geschnitten werden
            if(lu != null)
                retrieved.addAll(lu.retrieve(x,y,size));
            if(ld != null)
                retrieved.addAll(ld.retrieve(x,y,size));
            if(ru != null)
                retrieved.addAll(ru.retrieve(x,y,size));
            if(rd != null)
                retrieved.addAll(rd.retrieve(x,y,size));
            //alle "gesammelten" mapobjects zurückgeben
            retrieved.addAll(containedObj);
            return retrieved;
        }
        //leere liste wird zurückgegeben
        return new LinkedList<MapObject>();
    }

    public void add(MapObject m){
        //versucht so weit wie möglich unten das Mapobject einzufügen
        //wird einmalig für alle objects gemacht

        //wenn das jeweilige objekt reinpasst, es deinfügen
        if (fits(m, x, y, width / 2, height / 2)) {
            // checken ob child tree schon existiert, um diesen nicht zu überschreiben
            if (lu == null) {
                lu = new QuadTree(x, y, width / 2, height / 2);
            }
            lu.add(m);
        }else{
            if (fits(m,x, y + height/2, width/2, height/2)) {
                if (ld == null) {
                    ld = new QuadTree(x, y + height/2, width/2, height/2);
                }
                ld.add(m);
            }else{
                if (fits(m, x + width/2, y, width/2, height/2)) {
                    if (ru == null) {
                        ru = new QuadTree(x + width/2, y, width/2, height/2);
                    }
                    ru.add(m);
                }else{
                    if (fits(m, x + width/2, y + height/2, width/2, height/2)) {
                        if (rd == null) {
                            rd = new QuadTree(x + width/2, y + height/2, width/2, height/2);
                        }
                        rd.add(m);
                    }else{
                        //wenn das mapobject nicht in die kleineren sektoren passt, selber aufnehmen
                        //sozusagen ende der rekursion
                        containedObj.add(m);
                }
            }
        }
    }
}


    public void show(Graphics g){
        //zum debuggen genutzte visualisierungsfunktion
        g.setColor(Color.WHITE);
        g.drawRect(x,y,width,height);
        if(lu != null)
            lu.show(g);
        if(ld != null)
            ld.show(g);
        if(ru != null)
            ru.show(g);
        if(rd != null)
            rd.show(g);
    }


    private boolean fits(MapObject m, int x, int y, int width, int height){
        //prüft ob das objekt vollständig in den unterbaum passt
        return(!(
                m.getXpos() < x ||
                m.getXpos() + m.getXlen() > x + width ||
                m.getYpos() < y ||
                m.getYpos() + m.getYlen() > y + height
        ));
    }

    private boolean intersects(int x, int y, int size){
        //prüft ob der spieler den unterbaum schneidet
        //count variable zum zählen der berechnungen (vergleich mit alter methode)
        count++;
        return(!(
                x > this.x + this.width ||
                x + size < this.x ||
                y > this.y + this.height ||
                y + size < this.y
        ));
    }


}
