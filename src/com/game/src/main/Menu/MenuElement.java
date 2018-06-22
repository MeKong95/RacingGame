package com.game.src.main.Menu;

import java.awt.*;
import java.util.LinkedList;

abstract public class MenuElement {

    protected LinkedList<Rectangle> buttons = new LinkedList<Rectangle>();


    public abstract void render(Graphics g);


    public LinkedList<Rectangle> getButtons(){
        return buttons;
    }
}
