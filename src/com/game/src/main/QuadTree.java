package com.game.src.main;

// algorithm to lower number of collision calculations O(x) --> O(ln(x))
// https://de.wikipedia.org/wiki/Quadtree

import com.game.src.main.Objects.MapObject;
import java.awt.*;
import java.util.LinkedList;

public class QuadTree {
    private static int count = 0;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private QuadTree lu, ld, ru, rd; //recursive tree: left up, left down, right up, right down
    private final LinkedList<MapObject> containedObj = new LinkedList<MapObject>(); //objects in quad tree, not children

    public QuadTree(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public LinkedList<MapObject> retrieve(int x, int y, int size){
        LinkedList<MapObject> retrieved = new LinkedList<MapObject>();
        //gets every potentially collided rectangle
        if(intersects(x,y,size)){
            //when player collides with rectangle: testing rectangles kids for collision
            if(lu != null)
                retrieved.addAll(lu.retrieve(x,y,size));
            if(ld != null)
                retrieved.addAll(ld.retrieve(x,y,size));
            if(ru != null)
                retrieved.addAll(ru.retrieve(x,y,size));
            if(rd != null)
                retrieved.addAll(rd.retrieve(x,y,size));
            //give back collected map objects
            retrieved.addAll(containedObj);
            return retrieved;
        }
        //give back empty list
        return new LinkedList<MapObject>();
    }

    public void add(MapObject m){
        //tries to add map object below
        //once for every map object
        //when object fits: insert it
        if (fits(m, x, y, width / 2, height / 2)) {
            //check if child tree exists to not overwrite it
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
                        //when map object does not fit in smaller sector, add it to parent
                        //end of recursion
                        containedObj.add(m);
                }
            }
        }
    }
}

    public void show(Graphics g){
        //visualizing used for debugging
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
        //checks if object fits completely in child tree
        return(!(
                m.getXpos() < x ||
                m.getXpos() + m.getXlen() > x + width ||
                m.getYpos() < y ||
                m.getYpos() + m.getYlen() > y + height
        ));
    }

    private boolean intersects(int x, int y, int size){
        //checks if player touches child tree
        //count variable to count calculations (to compare with non quad tree method)
        count++;
        return(!(
                x > this.x + this.width ||
                x + size < this.x ||
                y > this.y + this.height ||
                y + size < this.y
        ));
    }
}
