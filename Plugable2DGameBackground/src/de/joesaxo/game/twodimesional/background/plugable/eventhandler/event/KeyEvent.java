package de.joesaxo.game.twodimesional.background.plugable.eventhandler.event;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;


/**
 * Created by Jens on 06.05.2017.
 */
public class KeyEvent extends java.awt.event.KeyEvent implements IEvent {

    private EventType eventType;

    public KeyEvent(EventType eventType, java.awt.event.KeyEvent keyEvent) {
        super(keyEvent.getComponent(), keyEvent.getID(), keyEvent.getWhen(), keyEvent.getModifiers(),
        keyEvent.getKeyCode(), keyEvent.getKeyChar(), keyEvent.getKeyLocation());
        this.eventType = eventType;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }
}
