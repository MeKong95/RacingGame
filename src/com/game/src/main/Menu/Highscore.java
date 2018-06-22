package com.game.src.main.Menu;

import com.game.src.main.Input.FileController;
import com.game.src.main.Game;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Highscore {

    private FileController fc1,fc2,fc3,fc4;
    private LinkedList<String> ll1,ll2,ll3,ll4;
    private Comparator<String> c;

    public Highscore(){

        c = new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        };

        fc1 = new FileController("res/highscores_"  +"1"+ ".crsp");
        fc2 = new FileController("res/highscores_"  +"2"+ ".crsp");
        fc3 = new FileController("res/highscores_"  +"3"+ ".crsp");
        fc4 = new FileController("res/highscores_"  +"4"+ ".crsp");
        ll1 = fc1.readScr();
        ll2 = fc2.readScr();
        ll3 = fc3.readScr();
        ll4 = fc4.readScr();
        Collections.sort(ll1,c);
        Collections.sort(ll2,c);
        Collections.sort(ll3,c);
        Collections.sort(ll4,c);
    }

    public void render(Graphics g){
        String str[];
        Graphics2D g2d = (Graphics2D) g;

        Font fnt1 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt1);
        g.setColor(Color.YELLOW);
        g.drawString("TOP TIMES FOR EVERY TRACK", Game.WIDTH/2 - 280, 150);

        Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.drawString("Track 1", Game.WIDTH*1/4 - 200, 250);
        int i = 0;

        while(i < ll1.size() && i < 5) {
            str = ll1.get(i).split(";");
            g.drawString(str[1] + "   " + str[0], Game.WIDTH * 1 / 4 - 200, 300+(i*50));
            i++;
        }
        i = 0;
        g.drawString("Track 2", Game.WIDTH*2/4 - 200, 250);
        while(i < ll2.size() && i < 5) {
            str = ll2.get(i).split(";");
            g.drawString(str[1] + "   " + str[0], Game.WIDTH * 2 / 4 - 200, 300+(i*50));
            i++;
        }
        i = 0;
        g.drawString("Track 3", Game.WIDTH*3/4 - 200, 250);
        while(i < ll3.size() && i < 5) {
            str = ll3.get(i).split(";");
            g.drawString(str[1] + "   " + str[0], Game.WIDTH * 3 / 4 - 200, 300+(i*50));
            i++;
        }
        i = 0;
        g.drawString("Track 4", Game.WIDTH*4/4 - 200, 250);
        while(i < ll4.size() && i < 5) {
            str = ll4.get(i).split(";");
            g.drawString(str[1] + "   " + str[0], Game.WIDTH * 4 / 4 - 200, 300+(i*50));
            i++;
        }

    }
}
