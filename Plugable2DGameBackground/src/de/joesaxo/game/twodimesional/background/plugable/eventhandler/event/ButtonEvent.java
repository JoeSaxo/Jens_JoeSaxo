package de.joesaxo.game.twodimesional.background.plugable.eventhandler.event;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;
import de.joesaxo.game.twodimesional.background.plugable.window.Button;

/**
 * Created by Jens on 08.05.2017.
 */
public class ButtonEvent implements IEvent {

    private EventType event;

    private Button button;

    public ButtonEvent(EventType event, Button button) {
        this.event = event;
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    @Override
    public EventType getEventType() {
        return event;
    }
}
