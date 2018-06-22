package com.game.src.main.Menu;

import com.game.src.main.Game;

import java.awt.*;
//https://stackoverflow.com/questions/37977143/get-an-input-from-the-user-using-graphics
public class Name extends MenuElement{

    public Rectangle playButton = new Rectangle(Game.WIDTH /2 -130, 400, 330,50);
    public Name(){
        buttons.add(playButton);
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Font fnt1 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt1);
        g.setColor(Color.YELLOW);
        g.drawString("NAME EINGABE", Game.WIDTH/2 - 180, 300);

        Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.drawString("CONTINUE (ENTER)", playButton.x+30, playButton.y+32);
        g2d.draw(playButton);

    }
}