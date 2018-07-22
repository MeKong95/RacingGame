package com.game.src.main.Menu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

abstract public class GameState {

    protected LinkedList<Button> buttons = new LinkedList<Button>();
    protected BufferedImage background = null;


    public abstract void render(Graphics g);


    public LinkedList<Button> getButtons(){
        return buttons;
    }
}
