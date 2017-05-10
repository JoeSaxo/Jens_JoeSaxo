package de.joesaxo.game.twodimesional.background.plugable.eventhandler.event;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;
import de.joesaxo.game.twodimesional.background.plugable.window.DrawFrame;
import de.joesaxo.game.twodimesional.background.plugable.window.WindowState;

import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.WINDOW_STATE_CHANGED_EVENT;

/**
 * Created by Jens on 06.05.2017.
 */
public class WindowStateEvent implements IEvent {

    private WindowState oldWindowState;
    private WindowState newWindowState;

    private DrawFrame frame;

    public WindowStateEvent(WindowState oldWindowState, WindowState newWindowState, DrawFrame frame) {
        this.oldWindowState = oldWindowState;
        this.newWindowState = newWindowState;
        this.frame = frame;
    }

    public WindowState getOldWindowState() {
        return oldWindowState;
    }

    public WindowState getNewWindowState() {
        return newWindowState;
    }

    public DrawFrame getDrawFrame() {
        return frame;
    }

    @Override
    public EventType getEventType() {
        return WINDOW_STATE_CHANGED_EVENT;
    }


}
