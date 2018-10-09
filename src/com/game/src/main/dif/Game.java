package dif;

import Input.ImageLoader;
import Input.KeyInput;
import Input.MapEditor;
import Input.MouseInput;
import Menu.*;
import Menu.Button;
import Menu.Menu;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    private static final int SCALE = 4;
    public static final int WIDTH = 320*SCALE;
    public static final int HEIGHT = WIDTH / 16 *9;
    private final String TITLE = "Jannis and Marco JAVA-Game";
    private boolean running = false;
    private Thread thread;
    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private static BufferedImage spriteSheet = null;
    private static BufferedImage plobj = null;
    private static BufferedImage mpobj = null;

    public static class debug{
        // zeigt die einzelnen quadranten des QuadTrees
        public static boolean showQTree = false;
        // zeigt die für die kollisionsdetektion wichtige mapobjects an
        public static boolean showObj = false;
        // stoppt die game loop
        public static boolean paused = false;
    }

    private static Menu m;
    private static Name n;
    private static Levelselect l;
    private static Highscore h;
    private static Race r;
    private static int track = 1;
    private static int allUpdates;
    private enum STATUS{
        MENU,
        GAME,
        NAME,
        LEVEL,
        SCORE
    }
    private static STATUS status = STATUS.MENU; //game starts in status MENU

    @SuppressWarnings("MagicConstant")
    public static void main(String args[]){
        Game instance = new Game(); //creates instance
        //sets frame size
        instance.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //creates frame
        JFrame frame = new JFrame(instance.TITLE);
        frame.add(instance);
        frame.pack(); //?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        instance.start();
    }

    private synchronized void start(){
        //limits number of frames to 1
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop(){
        if(!running)
            return;
        running = false;
        //try and catch for error management
        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.exit(1);
    }

    public void run(){ //solid game clock
        initialize();
        long lastTime = System.currentTimeMillis();
        final double amountOfTicks = 60.0;  //constant number of calculations per second
        double frameTime = 1000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        allUpdates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        while(running){
            long now = System.currentTimeMillis();           //first point of time
            delta += (now - lastTime) / frameTime;  //increasing variable by passed time
            lastTime = now;                         //preparation for test
            if(delta >= 1){                         //when variable reaches end of cycle everything is run
                tick();
                updates++;
                allUpdates++;
                delta = 0;                          //setback variable to zero
                render();
                frames++;
            }
            if(System.currentTimeMillis()-timer > 1000){
                timer +=1000;
                System.out.println(updates+ "Ticks," + frames + "FPS");
                updates=0;
                frames=0;
            }
            //Sleep command test
            /*try{
                Thread.sleep(10);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }*/
        }
        stop();
    }

    private void initialize(){
        ImageLoader loader = new ImageLoader();         // image loader for sprite sheet
        try{    // error-handling
            spriteSheet = loader.loadImage("/spritesheet.png");
        }catch(IOException e){
            e.printStackTrace();
        }
        ImageLoader fig = new ImageLoader();         // image loader for sprite sheet
        try{    // error-handling
            plobj = fig.loadImage("/plobjg.png");
        }catch(IOException e){
            e.printStackTrace();
        }
        ImageLoader gras = new ImageLoader();         // image loader for sprite sheet
        try{    // error-handling
            mpobj = gras.loadImage("/gras.png");
        }catch(IOException e){
            e.printStackTrace();
        }
        m = new Menu();
        l = new Levelselect();
        requestFocus();
        addKeyListener(new KeyInput());     //calls KeyInput class and hands over instance of game
        addMouseListener(new MouseInput());             //calls MouseInput class
        addMouseMotionListener(new MouseInput());
    }

    private void tick(){
        if(status == STATUS.GAME) {
            r.tick();
        }
    }

    private void render(){
        BufferStrategy strategy = this.getBufferStrategy();
        if(strategy == null){
            createBufferStrategy(3); //loads up to three images in advance
            return;
        }
        Graphics g = strategy.getDrawGraphics();
        g.drawImage(image, 0,0, getWidth(), getHeight(), this);
        switch(status) {
            case MENU:
                m.render(g);
                break;
            case GAME:
                r.render(g);
                MapEditor.render(g);
                break;
            case NAME:
                n.render(g);
                break;
            case LEVEL:
                l.render(g);
                break;
            case SCORE:
                h.render(g);
                break;
        }
        g.dispose();
        strategy.show();
    }

    // called when key is pressed
    public static void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch(status){
            case MENU:
                switch(key) {
                    case KeyEvent.VK_ESCAPE:
                        System.exit(1);
                        break;
                }
                break;
            case GAME:
                switch(key) {
                    case KeyEvent.VK_W:
                        r.getP().setAccY(-0.1);
                        break;
                    case KeyEvent.VK_A:
                        r.getP().setAccX(-0.1);
                        break;
                    case KeyEvent.VK_S:
                        r.getP().setAccY(0.1);
                        break;
                    case KeyEvent.VK_D:
                        r.getP().setAccX(0.1);
                        break;
                    case KeyEvent.VK_F2:
                        r = new Race(track, n.getName());
                        break;
                    case KeyEvent.VK_ESCAPE:
                        status = STATUS.MENU;
                        break;
                    case KeyEvent.VK_O:
                        debug.showObj = !debug.showObj;
                        break;
                    case KeyEvent.VK_L:
                        debug.showQTree = !debug.showQTree;
                        break;
                    case KeyEvent.VK_P:
                        // pausieren der game loop
                        debug.paused = !debug.paused;
                        break;
                }
                break;
            case NAME:
                switch(key){
                    case KeyEvent.VK_ENTER:
                        status = STATUS.LEVEL;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        status = STATUS.MENU;
                        break;
                    default:
                        n.addChar(e);
                        break;
                    }
                break;
            case LEVEL:
                switch(key) {
                    case KeyEvent.VK_ESCAPE:
                        status = STATUS.MENU;
                        break;
                }
                break;
            case SCORE:
                switch(key) {
                    case KeyEvent.VK_ESCAPE:
                        status = STATUS.MENU;
                        break;
                }
                break;
        }

    }

    //called when key is released
    public static void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        switch(status){
            case GAME:
                switch(key){
                    case KeyEvent.VK_W:
                        r.getP().setAccY(0);
                        break;
                    case KeyEvent.VK_A:
                        r.getP().setAccX(0);
                        break;
                    case KeyEvent.VK_S:
                        r.getP().setAccY(0);
                        break;
                    case KeyEvent.VK_D:
                        r.getP().setAccX(0);
                        break;
                }
        }
    }

    // called when mouse is pressed
    public static void mousePressed(MouseEvent e) {

        int temp;
        switch(status){
            case MENU:
                temp = checkButtons(m,e);
                switch(temp){
                    case 0:
                        //play button
                        n = new Name();
                        status = STATUS.NAME;
                        break;
                    case 1:
                        //score button
                        h = new Highscore();
                        status = STATUS.SCORE;
                        break;
                    case 2:
                        //exit button
                        System.exit(1);
                        break;
                    default:
                        break;
                    }
                    break;
            case NAME:
                temp = checkButtons(n,e);
                switch(temp){
                    case 0:
                        //enter button
                        status = STATUS.LEVEL;
                        break;
                    default:
                        break;
                }
                break;
            case LEVEL:
                temp = checkButtons(l,e);
                switch(temp){
                    case 0:
                        //track 1 button
                        track = 1;
                        r = new Race(track, n.getName());
                        status = STATUS.GAME;
                        break;
                    case 1:
                        //track 2 button
                        track = 2;
                        r = new Race(track, n.getName());
                        status = STATUS.GAME;
                        break;
                    case 2:
                        //track 3 button
                        track = 3;
                        r = new Race(track, n.getName());
                        status = STATUS.GAME;
                        break;
                    case 3:
                        //track 4 button
                        track = 4;
                        r = new Race(track, n.getName());
                        status = STATUS.GAME;
                        break;
                    default:
                        break;
                }
                break;
            case SCORE:
                temp = checkButtons(h,e);
                switch(temp) {
                    case 0:
                        //back button
                        status = STATUS.MENU;
                        break;
                }
                break;
            case GAME:
                MapEditor.add(e);
        }
    }

    // called when mouse is moved
    public static void mouseMoved(MouseEvent e) {
        int temp;
        switch(status){
            case MENU:
                temp = checkButtons(m,e);
                //play button
                //score button
                //exit button
                if(temp < m.getButtons().size())
                    m.getButtons().get(temp).startHovering();
                else
                    for (Button b: m.getButtons()
                         ) {
                        b.stopHovering();
                    }
                break;
            case NAME:
                temp = checkButtons(n,e);
                if(temp < n.getButtons().size())
                    n.getButtons().get(temp).startHovering();
                else
                    for (Button b: n.getButtons()
                            ) {
                        b.stopHovering();
                    }
                break;

            case LEVEL:
                temp = checkButtons(l,e);
                    //track 1 button
                    //track 2 button
                    //track 3 button
                    //track 4 button
                if(temp < l.getButtons().size())
                    l.getButtons().get(temp).startHovering();
                else
                    for (Button b: l.getButtons()
                            ) {
                        b.stopHovering();
                    }
                break;

            case SCORE:
                temp = checkButtons(h,e);
                if(temp < h.getButtons().size())
                    h.getButtons().get(temp).startHovering();
                else
                    for (Button b: h.getButtons()
                            ) {
                        b.stopHovering();
                    }
                break;
        }
    }


    private static int checkButtons(GameState m, MouseEvent e){
        int i;
        LinkedList<Button> buttons = m.getButtons();

        for(i = 0; i<buttons.size();i++)
            if(buttons.get(i).intersects(e.getX(), e.getY(), 1,1))
                break;
        return i;
    }

    public static int getTrack(){
        return track;
    }
    public static int getAllUpdates(){return allUpdates;}
    public static BufferedImage getSpriteSheet(){
        return spriteSheet;
    }
    public static BufferedImage getplobj(){return plobj;}
    public static BufferedImage getmpobj(){return mpobj;}
}

