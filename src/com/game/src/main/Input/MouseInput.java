package com.game.src.main.Input;

import com.game.src.main.Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    //where initialization occurs:
    //Register for mouse events on blankArea and the panel.
    //addMouseListener(new MouseInput());

    @Override
    public void mouseClicked(MouseEvent e) {
        Game.mousePressed(e);
    }

}
