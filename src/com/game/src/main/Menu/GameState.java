package Menu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

abstract public class GameState {
    final LinkedList<Button> buttons = new LinkedList<Button>();
    BufferedImage background = null;
    public abstract void render(Graphics g);
    public LinkedList<Button> getButtons(){
        return buttons;
    }
}
