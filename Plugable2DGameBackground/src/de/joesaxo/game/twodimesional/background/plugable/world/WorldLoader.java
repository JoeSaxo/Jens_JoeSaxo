package de.joesaxo.game.twodimesional.background.plugable.world;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jens on 05.05.2017.
 */
public class WorldLoader {

    public static final String relativeWorldPath = "\\Worlds";

    private File worldPath;

    public List<World> worlds;

    public WorldLoader(String path) {

        worldPath = new File(path + relativeWorldPath);

        worlds = new ArrayList<>();



    }

    public void loadWorlds() {

        worlds.clear();

        for (File file : worldPath.listFiles()) {
            if (file.isDirectory()) {
                World world = new World(file);
                worlds.add(world);
            }
        }
    }

    public World getWorld(int index) {
        return worlds.get(index);
    }


}
