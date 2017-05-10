package de.joesaxo.game.twodimesional.background.plugable.window;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.Event;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventHandler;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.MouseEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.PaintEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.WindowStateEvent;

import java.util.List;

import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.MOUSE_OVER_COMPONENT_EVENT;
import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.PAINT_EVENT;
import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.WINDOW_STATE_CHANGED_EVENT;

/**
 * Created by Jens on 08.05.2017.
 */
public abstract class Component {

    private DrawScreen screen;

    private boolean visible;

    private boolean active;

    public DrawFrame drawFrame;

    private int x, y, width, height;
    private boolean mouseOver;

    public Component(DrawFrame drawFrame, int x, int y, int width, int height) {
        this.drawFrame = drawFrame;
        EventHandler.add(this);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        visible = true;
    }

    protected boolean addScreen(DrawScreen screen) {
        if (this.screen != null) return false;
        this.screen = screen;
        return true;
    }

    protected void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    public void setDrawFrame(DrawFrame drawFrame) {
        this.drawFrame = drawFrame;
    }


    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }


    public DrawFrame getDrawFrame() {
        return drawFrame;
    }

    public abstract void draw(PaintEvent e);

    protected void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }
    
    public boolean mouseOver() {
        return mouseOver && isActive() && isVisible();
    }

    public boolean contains(int x, int y) {
        int deltaMouseX = x - getX();
        int deltaMouseY = y - getY();

        if (deltaMouseX >= 0 && deltaMouseX < getWidth()) {
            if (deltaMouseY >= 0 && deltaMouseY < getHeight()) {
                return true;
            }
        }

        return false;
    }

}
