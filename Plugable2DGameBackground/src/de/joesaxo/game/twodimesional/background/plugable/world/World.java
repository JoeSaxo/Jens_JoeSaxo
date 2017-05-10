package de.joesaxo.game.twodimesional.background.plugable.world;

import de.joesaxo.game.twodimesional.background.plugable.ArrayLayer;
import org.jarcraft.library.ImageTool;
import sun.plugin.javascript.navig4.Layer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jens on 05.05.2017.
 */
public class World {

    String name;

    ArrayLayer layers;

    public World(File worldDirectory) {

        name = worldDirectory.getName();

        File[] layerFiles = worldDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));


        layers = new ArrayLayer(layerFiles.length);

        for (int i = 0; i < layers.getLength(); i++) {
            layers.setElement(ImageTool.load(layerFiles[i]), i);
        }

        layers.mergesort();

    }

    public int layers() {
        return layers.getLength();
    }

    public BufferedImage getLayer(int index) {
        return layers.getElement(index);
    }

}
