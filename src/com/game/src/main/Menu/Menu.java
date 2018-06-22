package com.game.src.main.Menu;

import com.game.src.main.Game;

import java.awt.*;

public class Menu {

    public Rectangle playButton = new Rectangle(Game.WIDTH /2 -90, 400, 180,50);
    public Rectangle scoreButton = new Rectangle(Game.WIDTH /2 -90, 500, 280,50);
    public Rectangle quitButton = new Rectangle(Game.WIDTH /2 -90, 600, 180,50);

    public Menu(){

    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Font fnt1 = new Font("Gill Sans", Font.BOLD, 50);
        g.setFont(fnt1);
        g.setColor(Color.YELLOW);
        g.drawString("COOLES RENN SPEIL", Game.WIDTH/2 - 280, 300);

        Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.drawString("Play (F1)", playButton.x+30, playButton.y+32);
        g2d.draw(playButton);
        g.drawString("Highscore (F2)", scoreButton.x+19, scoreButton.y+32);
        g2d.draw(scoreButton);
        g.drawString("Quit (ESC)", quitButton.x+19, quitButton.y+32);
        g2d.draw(quitButton);
    }
}
