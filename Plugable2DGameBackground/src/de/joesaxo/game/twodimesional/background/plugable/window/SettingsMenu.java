package de.joesaxo.game.twodimesional.background.plugable.window;

import de.joesaxo.game.twodimesional.background.plugable.GameComponents;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.Event;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventHandler;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.ButtonEvent;
import org.jarcraft.library.ImageTool;

import java.awt.image.BufferedImage;
import java.io.File;

import static de.joesaxo.game.twodimesional.background.plugable.GameComponents.drawFrame;
import static de.joesaxo.game.twodimesional.background.plugable.window.WindowState.SETTINGS_MENU;

/**
 * Created by Jens on 07.05.2017.
 */
public class SettingsMenu {

    Picture background;

    Button startButton;

    Button settingsButton;
    Button backButton;

    public SettingsMenu() {

        EventHandler.add(this);

        File path = new File(GameComponents.path + "\\Data\\Settings\\");

        DrawScreen screen = new DrawScreen(drawFrame);
        screen.setWindowState(SETTINGS_MENU);
        BufferedImage imageBuffer;
        {
            imageBuffer = ImageTool.load(path, "Background.png");
            background = new Picture(drawFrame, imageBuffer);
        }/*
        {
            imageBuffer = ImageTool.load(path, "Start.png");
            int startButtonX = background.getWidth()/2- imageBuffer.getWidth()/2;
            startButton = new Button(drawFrame, imageBuffer, startButtonX - 50, 140, "Start");
        }
        {
            imageBuffer = ImageTool.load(path, "Settings.png");
            int startButtonX = background.getWidth()/2- imageBuffer.getWidth()/2;
            settingsButton = new Button(drawFrame, imageBuffer, startButtonX - 60, 220, "Settings");
        }//*/
        {
            imageBuffer = ImageTool.load(path, "Back.png");
            int startButtonX = background.getWidth()/2- imageBuffer.getWidth()/2;
            backButton = new Button(drawFrame, imageBuffer, startButtonX + 100, 220, "Back");
        }

        screen.setDefaultSize(background.getWidth(), background.getHeight());
        screen.add(background);
        //screen.add(startButton);
        //screen.add(settingsButton);
        screen.add(backButton);
    }

    @Event(EventType.BUTTON_CLICK_EVENT)
    public void but(ButtonEvent e) {
        if (e.getButton().equals(backButton)) {
            drawFrame.changeWindowState(WindowState.MAIN_MENU);
        }
    }

}
