package de.joesaxo.game.twodimesional.background.plugable.window;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventHandler;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static de.joesaxo.game.twodimesional.background.plugable.GameComponents.gameFrame;
import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.*;

/**
 * Created by Jens on 07.05.2017.
 */
public class EventMouseListener implements MouseListener, MouseMotionListener{

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        EventHandler.invoke(new MouseEvent(MOUSE_CLICK_EVENT, e));
    }

    boolean pressed;

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (!pressed) {
            pressed = true;
            EventHandler.invoke(new MouseEvent(MOUSE_PRESS_EVENT, e));
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        if (pressed) {
            pressed = false;
            EventHandler.invoke(new MouseEvent(MOUSE_RELEASE_EVENT, e));
        }
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        EventHandler.invoke(new MouseEvent(MOUSE_ENTER_EVENT, e));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        EventHandler.invoke(new MouseEvent(MOUSE_EXIT_EVENT, e));
    }

    public static int getMousePositionX() {
        return mouseX;
    }

    public static int getMousePositionY() {
        return mouseY;
    }

    private static int mouseX, mouseY;

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        EventHandler.invoke(new MouseEvent(MOUSE_DRAGGED_EVENT, e));
        EventHandler.invoke(new MouseEvent(MOUSE_CHANGED_POSITION_EVENT, e));
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        EventHandler.invoke(new MouseEvent(MOUSE_MOVED_EVENT, e));
        EventHandler.invoke(new MouseEvent(MOUSE_CHANGED_POSITION_EVENT, e));
    }
}
