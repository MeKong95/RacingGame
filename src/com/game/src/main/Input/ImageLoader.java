package com.game.src.main.Input;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

// to load picture from image file
public class ImageLoader {
    public BufferedImage loadImage(String path) throws IOException {
        //error handling
        return ImageIO.read(getClass().getResource(path));
    }
}
