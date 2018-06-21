import java.awt.*;

public class Levelselect {

    public Rectangle Level_1 = new Rectangle(Game.WIDTH /2 -90, 250, 180,50);
    public Rectangle Level_2 = new Rectangle(Game.WIDTH /2 -90, 350, 180,50);
    public Rectangle Level_3 = new Rectangle(Game.WIDTH /2 -90, 450, 180,50);
    public Rectangle Level_4 = new Rectangle(Game.WIDTH /2 -90, 550, 180,50);
    public Levelselect(){

    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.setColor(Color.YELLOW);
        g.drawString("Please choose the track of your choise (Navigate with Numbers on Keyboard)", Game.WIDTH/2 - 580, 200);

        Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.drawString("Track #1", Level_1.x+30, Level_1.y+32);
        g2d.draw(Level_1);
        g.drawString("Track #2", Level_2.x+30, Level_2.y+32);
        g2d.draw(Level_2);
        g.drawString("Track #3", Level_3.x+30, Level_3.y+32);
        g2d.draw(Level_3);
        g.drawString("Track #4", Level_4.x+30, Level_4.y+32);
        g2d.draw(Level_4);
    }
}
