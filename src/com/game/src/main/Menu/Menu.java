package Menu;

import dif.Game;
import Input.ImageLoader;
import java.awt.*;
import java.io.IOException;


public class Menu extends GameState{
    public Menu(){
        Button playButton = new Button(Game.WIDTH / 2 - 90, 400, 130, 50, "Play");
        buttons.add(playButton);
        Button scoreButton = new Button(Game.WIDTH / 2 - 90, 500, 230, 50, "Highscores");
        buttons.add(scoreButton);
        Button quitButton = new Button(Game.WIDTH / 2 - 90, 600, 130, 50, "Quit");
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
        g.drawString("RACE GAME", Game.WIDTH/2 - 200, 100);
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
