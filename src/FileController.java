import java.io.*;
import java.util.LinkedList;

public class FileController {
    private String p;
    private File f;

    public FileController(String path){
        p = path;
        f = new File(path);
    }

    public void write(String s, String n){
        //beschreiben der highscore datei
        // s: Zeit, n: Name
        try{
            FileWriter fw = new FileWriter(f, true); // true for appending
            PrintWriter pw = new PrintWriter(fw, true); // true for auto-flush
            pw.println(s + ";" + n);
            pw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public LinkedList<String[]> read(){
        //liest alles aus der Map_x.crsp datei aus und legt arrays der daten in eine liste
        String str;
        // = null wird verlangt
        LinkedList<String[]> Data = new LinkedList<String[]>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            //1. Spieler: xPos; yPos; startwinkel
            //2. GoalObject xPos; yPos; länge; breite
            //3. MapObject xPos; yPos; länge; breite
            while((str=br.readLine())!=null) {
                Data.add(str.split(";"));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return Data;

    }

     public LinkedList<String> readScr(){
        //liest highscore dateien und gibt eine liste von Strings zurück
        String str;
        LinkedList<String> Data = new LinkedList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            while((str=br.readLine())!=null) {
                Data.add(str);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return Data;

    }




}
