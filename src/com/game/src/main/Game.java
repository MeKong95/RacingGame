package com.game.src.main;

import com.game.src.main.Input.ImageLoader;
import com.game.src.main.Input.KeyInput;
import com.game.src.main.Input.MouseInput;
import com.game.src.main.Menu.*;
import com.game.src.main.Menu.Menu;
import com.game.src.main.Menu.GameState;

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
    public static final int SCALE = 4;
    public static final int WIDTH = 320*SCALE;
    public static final int HEIGHT = WIDTH / 16 *9;
    public final String TITLE = "Marco und Jannis Game";

    private boolean running = false;
    private Thread thread;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private static BufferedImage spriteSheet = null;

    public static JFrame frame;
    private static Menu m;
    private static Name n;
    private static Levelselect l;
    private static Highscore h;
    private static Race r;
    private static int track;
    private static int allUpdates; //schöneren variablennamen finden

    private enum STATUS{
        MENU,
        GAME,
        NAME,
        LEVEL,
        SCORE
    }
    private static STATUS status = STATUS.MENU; //Spiel startet im status MENU

    public static void main(String args[]){
        Game instance = new Game(); //erstellt instanz unseres spiels

        //legt standardgroesse des fensters fest
        instance.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //erstellen und konfigurieren des fensters
        frame = new JFrame(instance.TITLE);
        frame.add(instance);
        frame.pack(); //?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        instance.start();
    }

    private synchronized void start(){
        //verhindert erstellen von mehreren threads
        if(running)
            return;

        running = true;
        thread = new Thread(this);
        //startet thread und damit die run funktion
        thread.start();
    }

    private synchronized void stop(){
        if(!running)
            return;

        running = false;
        //java verlangt try und catch weil thread.join fehlschlagen kann
        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.exit(1);
    }

    public void run(){
        initialize();
        //solide game clock
        long lastTime = System.currentTimeMillis();
        final double amountOfTicks = 60.0;  //konstante für anzahl der berechnungen pro sekunde
        double frameTime = 1000 / amountOfTicks; //konstante für anzahl der nano sekunden
                                                       // pro berechnungszyklus
        double delta = 0;
        int updates = 0;
        allUpdates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        while(running){
            long now = System.currentTimeMillis();           // erster zeitstempel
            delta += (now - lastTime) / frameTime;  //vergrößern der zählvariable um die
                                                    // vergangene zeit und relativierung
            lastTime = now;                         // vorbereitung für den nächsten test
            if(delta >= 1){                         //wenn die zählvariable einen berechnungszyklus
                                                    // erreicht wird alles asugeführt
                tick();
                updates++;
                allUpdates++;
                delta--;                            //Zählvariable auf 0 zurücksetzen
            }
            render();
            frames++;
            if(System.currentTimeMillis()-timer > 1000){
                timer +=1000;
                System.out.println(updates+ "Ticks," + frames + "FPS");
                updates=0;
                frames=0;
            }
            //Sleep Befehl
            /*try{
                Thread.sleep(10);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }*/
        }
        stop();
    }

    public void initialize(){
        ImageLoader loader = new ImageLoader();         // immageloader fuer spritesheet
        try{    // error-handling, beim laden koennen fehler auftreten
            spriteSheet = loader.loadImage("/spritesheet.png");
        }catch(IOException e){
            e.printStackTrace();
        }

        m = new Menu();
        l = new Levelselect();
        requestFocus();
        addKeyListener(new KeyInput());     //ruft unsere KeyInput klasse auf und übergibt
                                                        // ihr die instanz unseres spiels
        addMouseListener(new MouseInput());             //ruft unsere MouseInput klasse auf
    }

    private void tick(){
        if(status == STATUS.GAME) {
            r.tick();
        }
    }

    private void render(){
        BufferStrategy strategy = this.getBufferStrategy();

        if(strategy == null){ //falls es noch keine strategie gibt erstelle eine neue
            createBufferStrategy(3); // strategie läd bis zu 3 bilder im vorraus
            return;
        }

        Graphics g = strategy.getDrawGraphics();

        g.drawImage(image, 0,0, getWidth(), getHeight(), this); //

        switch(status) {
            case MENU:
                m.render(g);
                break;
            case GAME:
                r.render(g);
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

    // diese funktion wird durch die KeyInput klasse beim drücken einer taste aufgerufen
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

    // diese funktion wird durch die KeyInput klasse beim drücken einer taste aufgerufen
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

    public static void mousePressed(MouseEvent e) {

        int temp;
        switch(status){
            case MENU:
                temp = checkClick(m,e);
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
                temp = checkClick(n,e);
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
                temp = checkClick(l,e);
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
                temp = checkClick(h,e);
                switch(temp) {
                    case 0:
                        //back button
                        status = STATUS.MENU;
                        break;
                }
                break;
        }
    }

    public static int checkClick(GameState m, MouseEvent e){
        int i;
        LinkedList<Rectangle> buttons = m.getButtons();

        for(i = 0; i<buttons.size();i++)
            if(buttons.get(i).intersects(e.getX(), e.getY(), 1,1))
                //ermittelt die nummer des ersten buttons der mit den Mauskoordinaten übereinstimmt
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
}

