package de.joesaxo.game.twodimesional.background.plugable.eventhandler.event;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;

import java.awt.*;


/**
 * Created by Jens on 06.05.2017.
 */
public class MouseEvent extends java.awt.event.MouseEvent implements IEvent {

    private EventType eventType;

    public MouseEvent(EventType eventType, java.awt.event.MouseEvent mouseEvent) {
        super(mouseEvent.getComponent(), mouseEvent.getID(), mouseEvent.getWhen(), mouseEvent.getModifiers(),
        mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getClickCount(), mouseEvent.isPopupTrigger(), mouseEvent.getButton());
        this.eventType = eventType;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }
}
