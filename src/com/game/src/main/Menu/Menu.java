package com.game.src.main.Menu;

import com.game.src.main.Game;
import com.game.src.main.Input.ImageLoader;

import java.awt.*;
import java.io.IOException;


public class Menu extends GameState{

    private Button playButton = new Button(Game.WIDTH /2 -90, 400, 180,50, "Play");
    private Button scoreButton = new Button(Game.WIDTH /2 -90, 500, 280,50, "Highscores");
    private Button quitButton = new Button(Game.WIDTH /2 -90, 600, 180,50, "Quit");

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



        for (Button b: buttons
                ) {
            b.render(g);
        }

        //Font fnt2 = new Font("Gill Sans", Font.BOLD, 30);
        //g.setFont(fnt2);
        //g.drawString("Play", playButton.x+30, playButton.y+35);
        //g2d.draw(playButton);
        //g.drawString("Highscore", scoreButton.x+19, scoreButton.y+35);
        //g2d.draw(scoreButton);
        //g.drawString("Quit", quitButton.x+30, quitButton.y+35);
        //g2d.draw(quitButton);
    }
}
