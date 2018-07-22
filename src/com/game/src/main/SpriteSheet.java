package com.game.src.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage image;

    // konstruktor benötigt das sprite sheet zur initialisierung
    public SpriteSheet(BufferedImage image){
        this.image = image;
    }

    public BufferedImage grabImage(int collumn, int row, int width, int height){
        //funktion zum extrahieren eines sprites aus unserem spritesheet

        return image.getSubimage((collumn * 32) - 32, (row * 32) - 32, width, height);
    }

}
