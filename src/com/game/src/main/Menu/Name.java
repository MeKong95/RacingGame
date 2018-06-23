package com.game.src.main.Menu;

import com.game.src.main.Game;
import com.game.src.main.Input.ImageLoader;
import com.game.src.main.Input.KeyInput;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

//https://stackoverflow.com/questions/37977143/get-an-input-from-the-user-using-graphics
public class Name extends MenuElement{

    private Rectangle playButton = new Rectangle(Game.WIDTH /2 -160, 600, 240,50);
    private Rectangle nameField = new Rectangle(Game.WIDTH /2 -100,200,250,30);
    private static BufferedImage background = null;

    private String name = "";

    public Name(){
        buttons.add(playButton);
        ImageLoader loader = new ImageLoader();
        try{
            background = loader.loadImage("/nameback.png");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addChar(KeyEvent e){
        System.out.println(name.length());
        if (e.getKeyCode()>=65&&e.getKeyCode()<=90){
            name+=e.getKeyChar();
        }//nur zeichen a bis z
        if (e.getKeyCode()==8){
            if (name.length()>0){
                name = name.substring(0, name.length() - 1);
            }
        }//fuer backspace/loeschen
    }

    public String getName(){
        if(name.length()==0){
            name="Anonym";
        }//ausnahme bei keiner eingabe ODER wieder geloeschter eingabe
        String s = name.substring(0,1).toUpperCase();
        name = s + name.substring(1);//Sorgt dafuer das unabhängig der Eingabe der erste Bchstb groß ist
        return name;
    }



    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g.drawImage(background,0,0,Game.WIDTH,Game.HEIGHT,null);

        Font fnt1 = new Font("arial", Font.BOLD, 60);
        g.setFont(fnt1);
        g.setColor(Color.BLACK);
        g.drawString(name.toUpperCase(), nameField.x, nameField.y+nameField.height);

        Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.drawString("CONTINUE", playButton.x+30, playButton.y+32);
        g2d.draw(playButton);
    }
}