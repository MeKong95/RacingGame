package com.game.src.main.Menu;

import com.game.src.main.Game;
import com.game.src.main.Input.KeyInput;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

//https://stackoverflow.com/questions/37977143/get-an-input-from-the-user-using-graphics
public class Name extends MenuElement{

    public Rectangle playButton = new Rectangle(Game.WIDTH /2 -130, 400, 330,50);
    Rectangle r = new Rectangle(Game.WIDTH /2 -130,200,250,30);
    static String n = "";
    public Name(){
        buttons.add(playButton);
    }

    public static void addChar(KeyEvent e){
        if (e.getKeyCode()>65&&e.getKeyCode()<90){
            n+=e.getKeyChar();
        }

        System.out.println(e);
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

        g.setColor(Color.yellow);
        g.fillRect(r.x, r.y, r.width, r.height);
        g.setColor(Color.black);
        g.drawString(n.toUpperCase(), r.x, r.y+r.height);

    }
}