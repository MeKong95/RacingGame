package com.game.src.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private final BufferedImage image;
    public SpriteSheet(BufferedImage image){
        this.image = image;
    } // constructor needs sprite sheet to initialize
    public BufferedImage grabImage(int column, int row, int width, int height){//extracts sprite from sheet
        return image.getSubimage((column * 32) - 32, (row * 32) - 32, width, height);
    }
}
