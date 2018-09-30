package com.game.src.main.Menu;

import com.game.src.main.Game;
import com.game.src.main.Input.ImageLoader;
import java.awt.*;
import java.io.IOException;

public class Levelselect extends GameState {
    public Levelselect(){
        Button level_1 = new Button(Game.WIDTH / 2 - 495, 60, 444, 230, "");
        buttons.add(level_1);
        Button level_2 = new Button(Game.WIDTH / 2 + 51, 80, 444, 210, "");
        buttons.add(level_2);
        Button level_3 = new Button(Game.WIDTH / 2 - 495, 368, 444, 215, "");
        buttons.add(level_3);
        Button level_4 = new Button(Game.WIDTH / 2 + 51, 368, 444, 215, "");
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
        g.setColor(Color.WHITE);
        g2d.drawString("Track 1",Game.WIDTH / 2-180,275);
        g2d.drawString("Track 2",Game.WIDTH / 2+380,275);
        g2d.drawString("Track 3",Game.WIDTH / 2-180,570);
        g2d.drawString("Track 4",Game.WIDTH / 2+380,570);
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
