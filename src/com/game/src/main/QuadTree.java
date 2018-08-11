package com.game.src.main;

// Algorithmus verringert anzahl der kollisionsberechnungen O(x) --> O(ln(x))
// https://de.wikipedia.org/wiki/Quadtree

import com.game.src.main.Objects.MapObject;

import java.util.LinkedList;

public class QuadTree {

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

        //sucht alle potenziell kollidierenden rechtecke
        if(intersects(x,y,size)){
            //wenn spieler das rechteck schneidet, testen welche kinder geschnitten werden
            if(lu != null)
                containedObj.addAll(lu.retrieve(x,y,size));
            if(ld != null)
                containedObj.addAll(ld.retrieve(x,y,size));
            if(ru != null)
                containedObj.addAll(ru.retrieve(x,y,size));
            if(rd != null)
                containedObj.addAll(rd.retrieve(x,y,size));
            //alle "gesammelten" mapobjects zurückgeben
            return containedObj;
        }
        //leere liste wird zurückgegeben
        return new LinkedList<MapObject>();
    }

    public boolean add(MapObject m){
        //versucht so weit wie möglich unten das Mapobject einzufügen
        //gibt bei erfolg true zurück
        if(!fits(m)) {
            return false;
        }else{
            lu = new QuadTree(x, y, width/2, height/2);
            if(!lu.add(m)){
                ld = new QuadTree(x, y + height/2, width/2, height/2);
                if(!lu.add(m)){
                    ru = new QuadTree(x + width/2, y, width/2, height/2);
                    if(!lu.add(m)){
                        rd = new QuadTree(x + width/2, y + height/2, width/2, height/2);
                        if(!lu.add(m)){
                            //wenn das mapobject nicht in die kleineren sektoren passt, selber aufnehmen
                            containedObj.add(m);
                        }
                    }
                }
            }
            return true;
        }

    }

    private boolean fits(MapObject m){
        return(!(
                m.getXpos() < this.x ||
                m.getXpos() + m.getXlen() > this.x + this.width ||
                m.getYpos() < this.y ||
                m.getYpos() + m.getYlen() > this.y + this.height
        ));
    }

    private boolean intersects(int x, int y, int size){
        return(!(
                x > this.x + this.width ||
                x + size < this.x ||
                y > this.y + this.height ||
                y + size < this.y
        ));
    }


}
