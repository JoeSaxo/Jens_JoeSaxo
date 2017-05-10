package de.joesaxo.game.twodimesional.background.plugable;

import de.joesaxo.game.twodimesional.background.plugable.window.DrawFrame;
import de.joesaxo.game.twodimesional.background.plugable.window.GameFrame;
import de.joesaxo.game.twodimesional.background.plugable.world.WorldLoader;

/**
 * Created by Jens on 06.05.2017.
 */
public final class GameComponents {

    private GameComponents() {}

    public static final String path = "C:\\Users\\Jens\\Documents\\My Games\\PlugWorld";

    public static EventListener eventListener;

    public static WorldLoader worldLoader;

    public static GameFrame gameFrame;

    public static DrawFrame drawFrame;

    public static GameConfig gameConfig;


}
