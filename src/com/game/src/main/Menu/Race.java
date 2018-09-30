package com.game.src.main.Menu;

import com.game.src.main.Game;
import com.game.src.main.Input.FileController;
import com.game.src.main.Input.ImageLoader;
import com.game.src.main.Objects.GoalObject;
import com.game.src.main.Objects.MapObject;
import com.game.src.main.Objects.Player;
import com.game.src.main.QuadTree;
import java.awt.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;

public class Race extends GameState{
    private final LinkedList<String> scores;
    private final QuadTree qTree = new QuadTree(0,0, Game.WIDTH, Game.HEIGHT);
    private final Player p;
    private final GoalObject goalObject;
    private final LinkedList<MapObject> listMapObjects = new LinkedList<MapObject>();
    private Image asphalt;
    private final LinkedList<Rectangle> StatusBar = new LinkedList<Rectangle>();

    public Race(int trackNr, String name){
        //List contains player,goal and map objects
        FileController map = new FileController("res/map_" + trackNr + ".crsp");
        FileController score = new FileController("res/highscores_" + trackNr + ".crsp");
        LinkedList<String[]> tempList = map.read();
        p = new Player(
                Double.parseDouble(tempList.get(0)[0]), // x coordinate
                Double.parseDouble(tempList.get(0)[1]), // y coordinate
                Double.parseDouble(tempList.get(0)[2]), // starting angle
                name
        );
        goalObject = new GoalObject(
                Double.parseDouble(tempList.get(1)[0]), // x coordinate
                Double.parseDouble(tempList.get(1)[1]), // y coordinate
                Double.parseDouble(tempList.get(1)[2]), // width
                Double.parseDouble(tempList.get(1)[3])); //height
        listMapObjects.clear();
        for(int i = 2; i<tempList.size();i++){
            // checks if map file contains empty strings
            //if(!tempList.get(i)[0].equals(""))
            // proper error handling is more efficient
            try {
                listMapObjects.add(new MapObject(
                        Double.parseDouble(tempList.get(i)[0]), //x coordinate
                        Double.parseDouble(tempList.get(i)[1]), //y coordinate
                        Double.parseDouble(tempList.get(i)[2]), // width
                        Double.parseDouble(tempList.get(i)[3]))); // height
            }catch(NumberFormatException nfe){
                nfe.printStackTrace();
            }
        }
        for (MapObject m:listMapObjects
             ) {
            qTree.add(m);
        }
        ImageLoader loader = new ImageLoader();
        try{
            asphalt = loader.loadImage("/asphalt.jpg");
        }catch(IOException e){
            e.printStackTrace();
        }
        scores = score.readScr();
        // return 0 if no digits found
        Comparator<String> c = new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }
            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        };
        scores.sort(c);
        StatusBar.add(new Rectangle(0, Game.HEIGHT - 100, Game.WIDTH / 4,200));
        StatusBar.add(new Rectangle(0, Game.HEIGHT - 100, Game.WIDTH * 3 / 4,200));
        StatusBar.add(new Rectangle(0, Game.HEIGHT - 100, Game.WIDTH * 4 / 4,200));
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.setColor(Color.WHITE);
        g.drawString("Player: " + p.getName(), 50, Game.HEIGHT-50);
        g.drawString("Top-Time: " + scores.getFirst().split(";")[0],1000, Game.HEIGHT-70);
        g.drawString("by: " + scores.getFirst().split(";")[1], 1000, Game.HEIGHT-30);
        g2d.setStroke(new BasicStroke(5));
        for (Rectangle r: StatusBar) {
            g2d.draw(r);
        }
        g.drawImage(asphalt, 0, 0, Game.WIDTH, Game.HEIGHT-100, null);
        goalObject.render(g);
        // go through list of map objects an render them
        for (MapObject l : listMapObjects) l.render(g);
        p.render(g);
    }

    public void tick(){
        p.tick(qTree);
        p.tick(goalObject);
    }

    public Player getP(){
        return p;
    }
}
