import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Game gameobj;

    // konstuktor erhält nur instanz vom spiel
    public KeyInput(Game instance){
        this.gameobj = instance;
    }

    //keyPressed wird durch die klasse KeyAdapter aufgerufen wenn eine taste gedrückt wird
    public void keyPressed(KeyEvent e){
        gameobj.keyPressed(e);  // weitergeben des KeyEvents an unsere game instanz
    }

    //keyReleased wird durch die klasse KeyAdapter aufgerufen wenn eine taste gedrückt wird
    public void keyReleased(KeyEvent e){
        gameobj.keyReleased(e); // weitergeben des KeyEvents an unsere game instanz
    }
}
