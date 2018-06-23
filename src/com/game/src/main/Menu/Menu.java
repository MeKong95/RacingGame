package com.game.src.main.Menu;

import com.game.src.main.Game;
import com.game.src.main.Input.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Menu extends MenuElement{

    private Rectangle playButton = new Rectangle(Game.WIDTH /2 -300, 550, 130,50);
    private Rectangle scoreButton = new Rectangle(Game.WIDTH /2 -90, 550, 180,50);
    private Rectangle quitButton = new Rectangle(Game.WIDTH /2 +170, 550, 130,50);
    private static BufferedImage background = null;


    public Menu(){
        buttons.add(playButton);
        buttons.add(scoreButton);
        buttons.add(quitButton);
        ImageLoader loader = new ImageLoader();
        try{
            background = loader.loadImage("/racetracksharp.png");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g.drawImage(background,0,0, Game.WIDTH, Game.HEIGHT,null);

        Font fnt1 = new Font("Gill Sans", Font.BOLD, 70);
        g.setFont(fnt1);
        g.setColor(Color.white);
        g.drawString("COOLES RENN SPEIL", Game.WIDTH/2 - 400, 100);

        Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.drawString("Play", playButton.x+30, playButton.y+35);
        g2d.draw(playButton);
        g.drawString("Highscore", scoreButton.x+19, scoreButton.y+35);
        g2d.draw(scoreButton);
        g.drawString("Quit", quitButton.x+30, quitButton.y+35);
        g2d.draw(quitButton);
    }
}
