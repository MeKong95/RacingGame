package com.game.src.main.Input;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

// klasse dient nur zum laden einer bilddatei in eine variable
public class ImageLoader {

    private BufferedImage image;

    public BufferedImage loadImage(String path) throws IOException {
        //.read fordert irgendein error handeling, da ein fehler auftreten kann, deshalb throws
        image = ImageIO.read(getClass().getResource(path));
        return image;
    }
}
