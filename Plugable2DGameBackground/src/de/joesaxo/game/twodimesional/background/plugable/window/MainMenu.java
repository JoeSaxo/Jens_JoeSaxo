package de.joesaxo.game.twodimesional.background.plugable.window;

import de.joesaxo.game.twodimesional.background.plugable.GameComponents;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.Event;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventHandler;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.ButtonEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.GameStateEvent;
import org.jarcraft.library.ImageTool;

import java.awt.image.BufferedImage;
import java.io.File;

import static de.joesaxo.game.twodimesional.background.plugable.GameComponents.drawFrame;
import static de.joesaxo.game.twodimesional.background.plugable.window.WindowState.MAIN_MENU;

/**
 * Created by Jens on 07.05.2017.
 */
public class MainMenu {

    Picture background;

    Button startButton;
    Button startButtonTwo;

    Button settingsButton;
    Button exitButton;

    public MainMenu() {

        EventHandler.add(this);

        File path = new File(GameComponents.path + "\\Data\\Mainmenu\\");

        DrawScreen screen = new DrawScreen(drawFrame);
        screen.setWindowState(MAIN_MENU);
        BufferedImage imageBuffer;
        {
            imageBuffer = ImageTool.load(path, "Background.png");
            background = new Picture(drawFrame, imageBuffer);
        }
        {
            imageBuffer = ImageTool.load(path, "Start.png");
            int startButtonX = background.getWidth()/2- imageBuffer.getWidth()/2;
            startButton = new Button(drawFrame, imageBuffer, startButtonX - 50, 140, "Start");
            startButtonTwo = new Button(drawFrame, imageBuffer, startButtonX - 40, 150, "StartTwo");
        }
        {
            imageBuffer = ImageTool.load(path, "Settings.png");
            int startButtonX = background.getWidth()/2- imageBuffer.getWidth()/2;
            settingsButton = new Button(drawFrame, imageBuffer, startButtonX - 60, 220, "Settings");
        }
        {
            imageBuffer = ImageTool.load(path, "Exit.png");
            int startButtonX = background.getWidth()/2- imageBuffer.getWidth()/2;
            exitButton = new Button(drawFrame, imageBuffer, startButtonX + 100, 220, "Exit");
        }

        screen.setDefaultSize(background.getWidth(), background.getHeight());
        screen.add(background);
        screen.add(startButton);
        screen.add(startButtonTwo);
        startButtonTwo.setVisible(false);
        screen.add(exitButton);
        screen.add(settingsButton);
    }

    @Event(EventType.BUTTON_CLICK_EVENT)
    public void but(ButtonEvent e) {
        System.out.println(e.getButton().getTitle());
        if (e.getButton().equals(exitButton)) {
            System.err.println("wrongButton");
            EventHandler.invoke(GameStateEvent.STOPPING);
        } else if (e.getButton().equals(settingsButton)) {
            drawFrame.changeWindowState(WindowState.SETTINGS_MENU);
        }
    }

}
