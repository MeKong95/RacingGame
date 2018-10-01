package Menu;

import dif.Game;
import Input.ImageLoader;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Name extends GameState{
    private String name = "";
    public Name(){
        Button playButton = new Button(Game.WIDTH / 2 - 160, 600, 240, 50, "Play");
        buttons.add(playButton);
        ImageLoader loader = new ImageLoader();
        try{
            background = loader.loadImage("/nameback.png");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addChar(KeyEvent e){
        if (e.getKeyCode()>=65&&e.getKeyCode()<=90 && name.length() < 8){
            name+=e.getKeyChar();
            if(name.length()==1)
                name = name.toUpperCase();
        }//only letters a/A to z/Z
        else if (e.getKeyCode()==8 && name.length()>0){
                name = name.substring(0, name.length() - 1);
        }//for delete/backspace
    }

    public String getName(){
        if(name.length()==0){
            name="Unknown";
        }//for missing or invalid input
        return name;
    }

    public void render(Graphics g){
        g.drawImage(background,0,0,Game.WIDTH,Game.HEIGHT,null);
        Font fnt1 = new Font("arial", Font.BOLD, 55);
        Font fnt2 = new Font("arial", Font.BOLD, 40);
        g.setFont(fnt1);
        g.setColor(Color.BLACK);
        g.drawString(name, Game.WIDTH /2 -100, 200+30);
        if(name.length()==0){
            g.setFont(fnt2);
            g.setColor(Color.GRAY);
            g.drawString("Please enter a name", Game.WIDTH /2 -100, 200+30);
        }
        for (Button b: buttons
                ) {
            b.render(g);
        }

        /*Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.drawString("CONTINUE", playButton.x+30, playButton.y+32);
        g2d.draw(playButton);*/
    }
}