package com.game.src.main.Input;

import com.game.src.main.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    //keyPressed called by key adapter when key is pressed
    public void keyPressed(KeyEvent e){
        Game.keyPressed(e);  // forward key event to game
    }

    //keyReleased called by key adapter when key is released
    public void keyReleased(KeyEvent e){
        Game.keyReleased(e); // forward key event to game
    }
}
