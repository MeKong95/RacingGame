package Menu;

import Input.FileController;
import dif.Game;
import Input.ImageLoader;
import java.awt.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;

public class Highscore extends GameState {
    private final LinkedList<String> ll1;
    private final LinkedList<String> ll2;
    private final LinkedList<String> ll3;
    private final LinkedList<String> ll4;
    public Highscore(){
        Button backButton = new Button(Game.WIDTH * 3 / 4, Game.HEIGHT * 3 / 4 + 50, 150, 40, "Back");
        buttons.add(backButton);
        Comparator<String> c = new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }
            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        };

        FileController fc1 = new FileController("src/com/game/src/res/highscores_" + "1");
        FileController fc2 = new FileController("src/com/game/src/res/highscores_" + "2");
        FileController fc3 = new FileController("src/com/game/src/res/highscores_" + "3");
        FileController fc4 = new FileController("src/com/game/src/res/highscores_" + "4");
        ll1 = fc1.readScr();
        ll2 = fc2.readScr();
        ll3 = fc3.readScr();
        ll4 = fc4.readScr();
        ll1.sort(c);
        ll2.sort(c);
        ll3.sort(c);
        ll4.sort(c);
        ImageLoader loader = new ImageLoader();
        try{
            background = loader.loadImage("/flag.png");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void render(Graphics g){
        String str[];
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background,0,0,Game.WIDTH,Game.HEIGHT/4, null);
        g.drawImage(background,0,Game.HEIGHT/4*3,Game.WIDTH,Game.HEIGHT/4, null);
        Font fnt1 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt1);
        g.setColor(Color.WHITE);
        g.drawString("HIGHSCORES", Game.WIDTH/2 - 200, 150);
        Font fnt2 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt2);
        g.drawString("Track 1", Game.WIDTH /4 - 200, 250);
        int i = 0;
        while(i < ll1.size() && i < 5) {
            str = ll1.get(i).split(";");
            g.drawString(str[0] + "   " + str[1], Game.WIDTH / 4 - 250, 300+(i*50));
            i++;
        }
        i = 0;
        g.drawString("Track 2", Game.WIDTH*2/4 - 200, 250);
        while(i < ll2.size() && i < 5) {
            str = ll2.get(i).split(";");
            g.drawString(str[0] + "   " + str[1], Game.WIDTH * 2 / 4 - 250, 300+(i*50));
            i++;
        }
        i = 0;
        g.drawString("Track 3", Game.WIDTH*3/4 - 200, 250);
        while(i < ll3.size() && i < 5) {
            str = ll3.get(i).split(";");
            g.drawString(str[0] + "   " + str[1], Game.WIDTH * 3 / 4 - 250, 300+(i*50));
            i++;
        }
        i = 0;
        g.drawString("Track 4", Game.WIDTH*4/4 - 200, 250);
        while(i < ll4.size() && i < 5) {
            str = ll4.get(i).split(";");
            g.drawString(str[0] + "   " + str[1], Game.WIDTH * 4 / 4 - 250, 300+(i*50));
            i++;
        }
        for (Button b: buttons
                ) {
            b.render(g);
        }
        /*g.setFont(fnt2);
        g.drawString("BACK", backButton.x+15, backButton.y+30);
        g2d.draw(backButton);*/

    }
}
