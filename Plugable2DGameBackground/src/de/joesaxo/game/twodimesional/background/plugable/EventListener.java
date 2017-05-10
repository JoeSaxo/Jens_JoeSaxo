package de.joesaxo.game.twodimesional.background.plugable;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.Event;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.GameStateEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.KeyEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.PaintEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.WindowStateEvent;
import de.joesaxo.game.twodimesional.background.plugable.window.MainMenu;
import de.joesaxo.game.twodimesional.background.plugable.window.SettingsMenu;
import de.joesaxo.game.twodimesional.background.plugable.window.WindowState;
import de.joesaxo.game.twodimesional.background.plugable.world.World;
import org.jarcraft.library.ImageTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static de.joesaxo.game.twodimesional.background.plugable.GameComponents.*;
import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.*;

/**
 * Created by Jens on 06.05.2017.
 */
public class EventListener {

    World activeWorld;

    @Event(START_EVENT)
    public void start(GameStateEvent e) {
        new MainMenu();
        new SettingsMenu();
        drawFrame.changeWindowState(WindowState.MAIN_MENU);
        //activeWorld = worldLoader.getWorld(0);
    }

    @Event(TICK_EVENT)
    public void tick(GameStateEvent e) {
        //activeWorld = worldLoader.getWorld(0);
    }

    @Event(PAINT_EVENT)
    public void paint(PaintEvent e) {
        if (drawFrame.getWindowState() == WindowState.MAIN_MENU) return;
        Graphics g = e.getGraphics();
        JLabel window = e.getDrawFrame();


        if (activeWorld == null) return;

        BufferedImage img = activeWorld.getLayer(0);

        if (img == null) return;

        //System.out.println(img.getWidth() + " | " + img.getHeight());

        if ((position) + gameConfig.getWindowWidth() >= img.getWidth()) position = img.getWidth() - gameConfig.getWindowWidth();

        img = ImageTool.extract(img, position, img.getHeight()-gameConfig.getWindowHeight()-90, gameConfig.getWindowWidth(), gameConfig.getWindowHeight());


        g.drawImage(img, 0, 0, window.getWidth(), window.getHeight(), null);

    }

    int position = 0;

    @Event(KEY_PRESSED_EVENT)
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                position++;
                break;
            case KeyEvent.VK_LEFT:
                if (position > 0) position--;
                break;
            default:
                break;
        }

    }

}
