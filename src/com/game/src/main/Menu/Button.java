package com.game.src.main.Menu;

import java.awt.*;

public class Button extends Rectangle {
    private String text;
    private int txtx;
    private int txty;
    private Font fnt = new Font("arial", Font.BOLD, 30);
    private Color color = new Color(200,100,20);
    private Stroke stroke = new BasicStroke(5);

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
        fnt = new Font("arial", Font.BOLD, 30);
        color = new Color(200,100,130);
        stroke = new BasicStroke(8);
        //aussehen ändern
    }

    public void stopHovering(){
        fnt  = new Font("arial", Font.BOLD, 30);
        color = new Color(200,100,20);
        stroke = new BasicStroke(5);
        //aussehen zurücksetzen
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g.setFont(fnt);
        g.setColor(color);
        ((Graphics2D) g).setStroke(stroke);
        g.drawString(text, x+txtx, y+txty);
        g2d.draw(this);
    }



}
