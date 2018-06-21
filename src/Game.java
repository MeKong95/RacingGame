import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
//Comment
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    public static final int SCALE = 4;
    public static final int WIDTH = 320*SCALE;
    public static final int HEIGHT = WIDTH / 16 *9;

    public final String TITLE = "Marco und Jannis Game";

    private boolean running = false;
    private Thread thread;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;

    private LinkedList<MapObject> listMapObjects = new LinkedList<MapObject>();
    private GoalObject goalObject;
    private Player p;
    private Menu m;
    private Name n;
    private Levelselect l;
    private Highscore h;
    private FileController map;

    private static int track;

    private enum STATUS{
        MENU,
        GAME,
        NAME,
        LEVEL,
        SCORE
    };

    private STATUS status = STATUS.MENU; //Spiel startet im status MENU

    public static void main(String args[]){
        Game instance = new Game(); //erstellt instanz unseres spiels

        //legt standardgroesse des fensters fest
        instance.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //erstellen und konfigurieren des fensters
        JFrame frame = new JFrame(instance.TITLE);
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
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;  //konstante für anzahl der berechnungen pro sekunde
        double frameTime = 1000000000 / amountOfTicks; //konstante für anzahl der nano sekunden
                                                       // pro berechnungszyklus
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        while(running){
            long now = System.nanoTime();           // erster zeitstempel
            delta += (now - lastTime) / frameTime;  //vergrößern der zählvariable um die
                                                    // vergangene zeit und relativierung
            lastTime = now;                         // vorbereitung für den nächsten test
            if(delta >= 1){                         //wenn die zählvariable einen berechnungszyklus
                                                    // erreicht wird alles asugeführt
                tick();
              //  updates++;
                delta--;                            //Zählvariable auf 0 zurücksetzen
            }
            render();
            //frames++;
            /*if(System.currentTimeMillis()-timer > 1000){
                timer +=1000;
                System.out.println(updates+ "Tickes," + frames + "FPS");
                updates=0;
                frames=0;
            }*/
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

        ImageLoader loader = new ImageLoader();         // ruft die imageloader klasse auf um
                                                        // unser sprite sheet zu laden
        try{    // java fordert error handling, weil beim laden von dateien fehler auftreten können
            spriteSheet = loader.loadImage("/spritesheet.png");
        }catch(IOException e){
            e.printStackTrace();
        }

        m = new Menu();
        n = new Name();
        l = new Levelselect();


        requestFocus();

        addKeyListener(new KeyInput(this)); //ruft unsere KeyInput klasse auf und übergibt
                                            // ihr die instanz unseres spiels


    }

    private void tick(){
        if(status == STATUS.GAME) {
            p.tick(listMapObjects);
            p.tick(goalObject);
        }
    }

    private void render(){
        BufferStrategy strategy = this.getBufferStrategy();

        if(strategy == null){ //falls es noch keine strategie gibt erstelle eine neue
            createBufferStrategy(3); // strategie läd bis zu 3 bilder im vorraus
            return;
        }

        Graphics g = strategy.getDrawGraphics();
        //////////////////

        g.drawImage(image, 0,0, getWidth(), getHeight(), this); //

        if(status == STATUS.GAME) {
            p.render(g);
            goalObject.render(g);
            // durchlaufen der liste von map objects und rendern dieser
            for(int i = 0; i < listMapObjects.size(); i++){
                listMapObjects.get(i).render(g);


            }
        }else if(status == STATUS.MENU){
            m.render(g);
        } else if (status == STATUS.NAME) {
            n.render(g);
        } else if (status == STATUS.LEVEL) {
            l.render(g);
        } else if (status == STATUS.SCORE) {
            h.render(g);
        }




        ///////////////////
        g.dispose();
        strategy.show();


    }

    // diese funktion wird durch die KeyInput klasse beim drücken einer taste aufgerufen
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        switch(key){
            case KeyEvent.VK_W:
                p.setAccY(-0.1);
                break;
            case KeyEvent.VK_A:
                p.setAccX(-0.1);
                break;
            case KeyEvent.VK_S:
                p.setAccY(0.1);
                break;
            case KeyEvent.VK_D:
                p.setAccX(0.1);
                break;
            case KeyEvent.VK_F1:
                if(status == STATUS.MENU) { //if wieder hinzugefügt da funktion des restart sonst im Game doppelt belegt
                    status = STATUS.NAME;
                }
                break;
            case KeyEvent.VK_F2:
                if(status == STATUS.GAME){
                    initMap(track);
                }
                else if(status==STATUS.MENU){
                    // Highscore wird hier erstellt damit zeiten nachdem sie aufgestellt wurden direkt angezeigt werden können
                    h = new Highscore();
                    status=STATUS.SCORE;
                }
                break;
            case KeyEvent.VK_ENTER:
                if (status==STATUS.NAME) {
                    status = STATUS.LEVEL;
                }
                break;
            case KeyEvent.VK_1:
                if (status==STATUS.LEVEL){
                    track = 1;
                    initMap(track);
                    status = STATUS.GAME;
                }
                break;
            case KeyEvent.VK_2:
                if (status==STATUS.LEVEL){
                    track = 2;
                    initMap(track);
                    status = STATUS.GAME;
                }
                break;
            case KeyEvent.VK_3:
                if (status==STATUS.LEVEL){
                    track = 3;
                    initMap(track);
                    status = STATUS.GAME;
                }
                break;
            case KeyEvent.VK_4:
                if (status==STATUS.LEVEL){
                    track = 4;
                    initMap(track);
                    status = STATUS.GAME;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if(status==STATUS.GAME) {
                    status = STATUS.MENU;
                }
                else if(status==STATUS.LEVEL){
                    status = STATUS.MENU;
                }
                else if(status==STATUS.NAME){
                    status = STATUS.MENU;
                }
                else if(status==STATUS.SCORE){
                    status = STATUS.MENU;
                }
                else if(status==STATUS.MENU){
                    System.exit(0);
                }
                break;
        }
    }

    // diese funktion wird durch die KeyInput klasse beim drücken einer taste aufgerufen
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        switch(key){
            case KeyEvent.VK_W:
                p.setAccY(0);
                break;
            case KeyEvent.VK_A:
                p.setAccX(0);
                break;
            case KeyEvent.VK_S:
                p.setAccY(0);
                break;
            case KeyEvent.VK_D:
                p.setAccX(0);
                break;
            }
    }

    public void initMap(int nr){
        //liste enthält Spieler, Ziel und Mapobjects
        LinkedList<String[]> tempList;
        map = new FileController("res/map_" + nr + ".crsp");
        tempList= map.read();
        p = new Player( Double.parseDouble(tempList.get(0)[0]),
                        Double.parseDouble(tempList.get(0)[1]),
                        Double.parseDouble(tempList.get(0)[2]),
                        this);
        goalObject = new GoalObject(Double.parseDouble(tempList.get(1)[0]),
                                    Double.parseDouble(tempList.get(1)[1]),
                                    Double.parseDouble(tempList.get(1)[2]),
                                    Double.parseDouble(tempList.get(1)[3]),
                                    this );
        listMapObjects.clear();
        for(int i = 2; i<tempList.size();i++){
            listMapObjects.add(new MapObject(   Double.parseDouble(tempList.get(i)[0]),
                                                Double.parseDouble(tempList.get(i)[1]),
                                                Double.parseDouble(tempList.get(i)[2]),
                                                Double.parseDouble(tempList.get(i)[3]),
                                                this));
        }
    }

    public static int getTrack(){
        return track;
    }


    public BufferedImage getSpriteSheet(){
        return spriteSheet;
    }


}

