package de.joesaxo.game.twodimesional.background.plugable.window;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventHandler;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.IEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.KeyEvent;

import java.awt.event.KeyListener;

import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.KEY_PRESSED_EVENT;
import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.KEY_RELEASED_EVENT;
import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.KEY_TYPED_EVENT;

/**
 * Created by Jens on 06.05.2017.
 */
public class EventKeyListener implements KeyListener {

    @Override
    public void keyTyped(java.awt.event.KeyEvent keyEvent) {
        IEvent e = new KeyEvent(KEY_TYPED_EVENT, keyEvent);
        EventHandler.invoke(e);
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent keyEvent) {
        IEvent e = new KeyEvent(KEY_PRESSED_EVENT, keyEvent);
        EventHandler.invoke(e);
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent keyEvent) {
        IEvent e = new KeyEvent(KEY_RELEASED_EVENT, keyEvent);
        EventHandler.invoke(e);
    }
}
