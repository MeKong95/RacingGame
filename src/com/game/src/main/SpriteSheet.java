package com.game.src.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private final BufferedImage image;

    // constructor needs sprite sheet to initialize
    public SpriteSheet(BufferedImage image){
        this.image = image;
    }

    public BufferedImage grabImage(int column, int row, int width, int height){
        //extracts sprite from sheet

        return image.getSubimage((column * 32) - 32, (row * 32) - 32, width, height);
    }

}
