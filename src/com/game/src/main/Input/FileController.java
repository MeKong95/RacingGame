package com.game.src.main.Input;

import java.io.*;
import java.util.LinkedList;

public class FileController {
    private final File f;

    public FileController(String path){
        f = new File(path);
    }

    public void write(String s, String n){
        //to write in high score files
        // s: Time, n: Name
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
        //reads everything in map files and creates array in a list
        String str;
        LinkedList<String[]> Data = new LinkedList<String[]>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            //1. Player: xPos; yPos; angle
            //2. GoalObject xPos; yPos; length; width
            //3. MapObject xPos; yPos; length; width
            while((str=br.readLine())!=null) {
                Data.add(str.split(";"));
            }
            System.out.println("Files read successfully");
        }catch(IOException e){
            e.printStackTrace();
        }
        return Data;

    }

     public LinkedList<String> readScr(){
        //reads from high score files and gives back list of strings
        String str;
        LinkedList<String> Data = new LinkedList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            while((str=br.readLine())!=null) {
                Data.add(str);
            }
            System.out.println("Files read successfully");
        }catch(IOException e){
            e.printStackTrace();
        }
        return Data;

    }




}
