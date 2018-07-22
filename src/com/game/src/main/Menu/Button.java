package com.game.src.main.Menu;

import java.awt.*;

public class Button extends Rectangle {
    private String text;
    private int txtx;
    private int txty;

    public Button(int x, int y, int width, int height, String text){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.txtx = 30;
        this.txty = 32;
    }

    public Button(int x, int y, int width, int height, int txtx, int txty, String text){
        //kann benutzt werden um text um verschiedene werte zu offseten
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.txtx = txtx;
        this.txty = txty;
    }

    public void startHovering(){
        //aussehen ändern
    }

    public void stopHovering(){
        //aussehen zurücksetzen
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.drawString(text, x+txtx, y+txty);
        g2d.draw(this);
    }



}
