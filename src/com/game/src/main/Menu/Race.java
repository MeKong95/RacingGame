package com.game.src.main.Menu;

import com.game.src.main.Game;
import com.game.src.main.Input.FileController;
import com.game.src.main.Input.ImageLoader;
import com.game.src.main.Objects.GoalObject;
import com.game.src.main.Objects.MapObject;
import com.game.src.main.Objects.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;


public class Race extends GameState{

    private Player p;
    private GoalObject goalObject;
    private LinkedList<MapObject> listMapObjects = new LinkedList<>();
    private FileController map;


    public Race(int trackNr, String name){

        //liste enthält Spieler, Ziel und Mapobjects
        LinkedList<String[]> tempList;
        map = new FileController("res/map_" + trackNr + ".crsp");
        tempList= map.read();
        p = new Player(
                Double.parseDouble(tempList.get(0)[0]),
                Double.parseDouble(tempList.get(0)[1]),
                Double.parseDouble(tempList.get(0)[2]),
                name);
        goalObject = new GoalObject(
                Double.parseDouble(tempList.get(1)[0]),
                Double.parseDouble(tempList.get(1)[1]),
                Double.parseDouble(tempList.get(1)[2]),
                Double.parseDouble(tempList.get(1)[3]));
        listMapObjects.clear();
        for(int i = 2; i<tempList.size();i++){
            listMapObjects.add(new MapObject(
                    Double.parseDouble(tempList.get(i)[0]),
                    Double.parseDouble(tempList.get(i)[1]),
                    Double.parseDouble(tempList.get(i)[2]),
                    Double.parseDouble(tempList.get(i)[3])));
        }
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        p.render(g);
        goalObject.render(g);
        // durchlaufen der liste von map objects und rendern dieser
        for (int i = 0; i < listMapObjects.size(); i++)
            listMapObjects.get(i).render(g);

    }

    public void tick(){
        p.tick(listMapObjects);
        p.tick(goalObject);
    }

    public Player getP(){
        return p;
    }

}
