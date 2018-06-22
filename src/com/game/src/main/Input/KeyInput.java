package com.game.src.main.Input;

import com.game.src.main.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    //keyPressed wird durch die klasse KeyAdapter aufgerufen wenn eine taste gedrückt wird
    public void keyPressed(KeyEvent e){
        Game.keyPressed(e);  // weitergeben des KeyEvents an unsere game instanz
    }

    //keyReleased wird durch die klasse KeyAdapter aufgerufen wenn eine taste gedrückt wird
    public void keyReleased(KeyEvent e){
        Game.keyReleased(e); // weitergeben des KeyEvents an unsere game instanz
    }
}
