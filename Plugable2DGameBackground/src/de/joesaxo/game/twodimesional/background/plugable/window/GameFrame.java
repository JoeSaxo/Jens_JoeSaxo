package de.joesaxo.game.twodimesional.background.plugable.window;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.Event;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventHandler;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.GameStateEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.KeyEvent;

import javax.swing.*;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static de.joesaxo.game.twodimesional.background.plugable.GameComponents.drawFrame;
import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.*;

/**
 * Created by Jens on 05.05.2017.
 */
public class GameFrame {



    private JFrame window;

    public  GameFrame() {
        EventHandler.add(this);
        init();
    }

    private void init() {
        window = new JFrame();
        //window.setAlwaysOnTop(true);
        //setFullscreen();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        drawFrame = new DrawFrame();
        System.out.println(window.getInsets().top);

        window.add(drawFrame);
        createListeners();

        window.setVisible(true);
    }

    private void createListeners() {
        window.getContentPane().addKeyListener(new EventKeyListener());
        EventMouseListener eventMouseListener = new EventMouseListener();
        drawFrame.addMouseListener(eventMouseListener);
        drawFrame.addMouseMotionListener(eventMouseListener);
    }

    public void setFullscreen() {
        window.setResizable(false);
        window.setUndecorated(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public JFrame getWindow() {
        return window;
    }

    @Event(STOP_EVENT)
    public void stopWindow(GameStateEvent e) {
        window.dispose();
    }

    @Event(TICK_EVENT)
    public void stopOnWindowClose(GameStateEvent e) {
        if (!window.isDisplayable()) {
            EventHandler.invoke(GameStateEvent.STOPPING);
        }
    }

    @Event(KEY_PRESSED_EVENT)
    public void e(KeyEvent e) {
        System.out.println(e.getKeyCode() + " | " + KeyEvent.VK_E);
        if (e.getKeyCode() == KeyEvent.VK_E) {
            EventHandler.invoke(GameStateEvent.STOPPING);
        }
    }

}
