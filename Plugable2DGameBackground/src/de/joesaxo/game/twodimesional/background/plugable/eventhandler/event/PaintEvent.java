package de.joesaxo.game.twodimesional.background.plugable.eventhandler.event;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;
import de.joesaxo.game.twodimesional.background.plugable.window.DrawFrame;

import javax.swing.*;
import java.awt.*;

import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.PAINT_EVENT;

/**
 * Created by Jens on 06.05.2017.
 */
public class PaintEvent implements IEvent {

    Graphics graphics;

    DrawFrame drawFrame;

    public PaintEvent(Graphics graphics, DrawFrame drawFrame) {
        this.graphics = graphics;
        this.drawFrame = drawFrame;
    }

    @Override
    public EventType getEventType() {
        return PAINT_EVENT;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public DrawFrame getDrawFrame() {
        return drawFrame;
    }
}
