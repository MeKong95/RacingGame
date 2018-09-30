package com.game.src.main.Menu;

import com.game.src.main.Game;
import com.game.src.main.Input.ImageLoader;
import java.awt.*;
import java.io.IOException;

public class Levelselect extends GameState {


    public Levelselect(){
        Button level_1 = new Button(Game.WIDTH / 2 - 495, 420, 180, 50, "Track 1");
        buttons.add(level_1);
        Button level_2 = new Button(Game.WIDTH / 2 - 225, 420, 180, 50, "Track 2");
        buttons.add(level_2);
        Button level_3 = new Button(Game.WIDTH / 2 + 45, 420, 180, 50, "Track 3");
        buttons.add(level_3);
        Button level_4 = new Button(Game.WIDTH / 2 + 315, 420, 180, 50, "Track 4");
        buttons.add(level_4);
        ImageLoader loader = new ImageLoader();
        try{
            background = loader.loadImage("/racetracklight.png");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g.drawImage(background,0,0,Game.WIDTH,Game.HEIGHT,null);

        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.setColor(Color.BLACK);
        g.drawString("Please choose a track", Game.WIDTH/2 - 350, 200);

        for (Button b: buttons
             ) {
            b.render(g);
        }

        /*Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.drawString("Track #1", Level_1.x+30, Level_1.y+32);
        g2d.draw(Level_1);
        g.drawString("Track #2", Level_2.x+30, Level_2.y+32);
        g2d.draw(Level_2);
        g.drawString("Track #3", Level_3.x+30, Level_3.y+32);
        g2d.draw(Level_3);
        g.drawString("Track #4", Level_4.x+30, Level_4.y+32);
        g2d.draw(Level_4);*/
    }
}
