package de.joesaxo.game.twodimesional.background.plugable.eventhandler.event;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;
import de.joesaxo.game.twodimesional.background.plugable.window.Component;

/**
 * Created by Jens on 09.05.2017.
 */
public class WindowComponentEvent implements IEvent {

    Component component;

    EventType event;

    public  WindowComponentEvent(EventType event, Component component) {
        this.component = component;
        this.event = event;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public EventType getEventType() {
        return event;
    }
}
