package org.jarcraft.library;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jens on 05.05.2017.
 */
public class ImageTool {

    public static BufferedImage copy(BufferedImage img) {
        BufferedImage bufferImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                bufferImage.setRGB(x, y, img.getRGB(x, y));
            }
        }

        return bufferImage;
    }

    public static BufferedImage flipX(BufferedImage img) {

        img = copy(img);

        for (int x = 0; x < img.getWidth() / 2; x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                exchange(img, x, y, img.getWidth() - 1 - x, y);
            }
        }

        return img;
    }

    public static BufferedImage flipY(BufferedImage img) {

        img = copy(img);

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight() / 2; y++) {
                exchange(img, x, y, x, img.getHeight() - 1 - y);
            }
        }

        return img;
    }

    public static BufferedImage rotate180Degree(BufferedImage img) {

        img = copy(img);

        for (int x = 0; x < (img.getWidth() / 2); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                exchange(img, x, y, img.getWidth() - 1 - x,img.getHeight() - 1 - y);
            }
        }
        if (img.getWidth()%2 != 0) {
            int x = img.getWidth() / 2;
            for (int y = 0; y < img.getHeight() / 2; y++) {
                exchange(img, x, y, x, img.getHeight() - 1 - y);
            }
        }

        return img;
    }
    
    public static BufferedImage rotate(BufferedImage img, boolean clockWise) {

        BufferedImage bufferImage = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int newX, newY;
                if (clockWise) {
                    newX = img.getHeight()-1-y;
                    newY = x;
                } else {
                    newX = y;
                    newY = img.getWidth()-1-x;
                }
                bufferImage.setRGB(newX, newY, img.getRGB(x, y));
            }
        }

        return bufferImage;
    }

    public static BufferedImage exchange(BufferedImage img, int xOne, int yOne, int xTwo, int yTwo) {
        //System.out.println("1. [ " + img.getRGB(xOne, yOne) + " | " + img.getRGB(xTwo, yTwo) + " ]");
        int rgbBuffer = img.getRGB(xOne, yOne);
        img.setRGB(xOne, yOne, img.getRGB(xTwo, yTwo));
        img.setRGB(xTwo, yTwo, rgbBuffer);
        //System.out.println("2. [ " + img.getRGB(xOne, yOne) + " | " + img.getRGB(xTwo, yTwo) + " ]");
        return img;
    }

    public static BufferedImage load(File path) {
        try {
            return ImageIO.read(path);
        } catch (IOException e) {
            return null;
        }
    }

    public static BufferedImage load(File path, String fileName) {
        return load(new File(path + File.separator + fileName));
    }

    public static BufferedImage extract(BufferedImage img, int x, int y, int width, int height) {
        BufferedImage bufferImage = new BufferedImage(width, height, img.getType());

        for (int relativeX = 0; relativeX < width; relativeX++) {
            for (int relativeY = 0; relativeY < height; relativeY++) {
                bufferImage.setRGB(relativeX, relativeY, img.getRGB(x + relativeX, y + relativeY));
            }
        }

        return bufferImage;
    }
}
