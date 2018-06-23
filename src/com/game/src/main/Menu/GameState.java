package com.game.src.main.Menu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

abstract public class GameState {

    protected LinkedList<Rectangle> buttons = new LinkedList<Rectangle>();
    protected static BufferedImage background = null;


    public abstract void render(Graphics g);


    public LinkedList<Rectangle> getButtons(){
        return buttons;
    }
}
