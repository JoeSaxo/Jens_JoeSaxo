package de.joesaxo.game.twodimesional.background.plugable.window;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.Event;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventHandler;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.MouseEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.PaintEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.WindowComponentEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.WindowStateEvent;

import java.util.ArrayList;
import java.util.List;

import static de.joesaxo.game.twodimesional.background.plugable.GameComponents.drawFrame;
import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.*;

/**
 * Created by Jens on 08.05.2017.
 */
public class DrawScreen {

    private DrawFrame frame;
    private WindowState[] visibleWindowStates;

    private boolean visible;

    int defaultWidth, defaultHeight;

    public void setWindowState(WindowState[] visibleWindowStates) {
        this.visibleWindowStates = visibleWindowStates;
    }

    public void setWindowState(WindowState visibleWindowState) {
        visibleWindowStates = new WindowState[]{visibleWindowState};
    }

    public void setDefaultSize(int defaultWidth, int defaultHeight) {
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
    }

    public int getDefaultWidth() {
        return defaultWidth;
    }

    public int getDefaultHeight() {
        return defaultHeight;
    }

    @Event(WINDOW_STATE_CHANGED_EVENT)
    public void windowStateChanged(WindowStateEvent e) {
        if (e.getDrawFrame().equals(drawFrame)) {
            for (WindowState windowState : visibleWindowStates) {
                if (windowState.equals(e.getOldWindowState())) {
                    visible = false;
                    for (Component component : components) {
                        component.setActive(false);
                    }
                } else if (windowState.equals(e.getNewWindowState())) {
                    visible = true;
                    for (Component component : components) {
                        component.setActive(true);
                    }
                }
            }
        }
    }

    @Event(PAINT_EVENT)
    public void paint(PaintEvent e) {
        if (visible) {
            if (defaultWidth != 0 && defaultHeight != 0) {
                e.getDrawFrame().setRelativeBounds(defaultWidth, defaultHeight);
            }
            for (Component component : components) {
                if (component.isActive() && component.isVisible()) {
                    component.draw(e);
                }
            }
        }
    }

    @Event(MOUSE_CHANGED_POSITION_EVENT)
    public void mouseMoved(MouseEvent e) {
        mouseOverUpdate(frame.convertToRelativeX(e.getX()), frame.convertToRelativeY(e.getY()));
    }

    public void mouseOverUpdate(int x, int y) {
        boolean mouseOverComponent = false;
        for (int i = components.size()-1; i >= 0; i--) {
            if (!mouseOverComponent && components.get(i).isVisible() && components.get(i).contains(x, y)) {
                mouseOverComponent = true;
                components.get(i).setMouseOver(true);
                EventHandler.invoke(new WindowComponentEvent(MOUSE_OVER_COMPONENT_EVENT, components.get(i)));
            } else {
                components.get(i).setMouseOver(false);
            }
        }
    }

    private List<Component> components;

    public DrawScreen(DrawFrame frame) {
        EventHandler.add(this);
        components = new ArrayList<>();
        this.frame = frame;
    }

    public boolean add(Component component) {
        if (component.addScreen(this)) {
            components.add(component);
            if (visible) component.setActive(true);
            return true;
        }
        return false;
    }
}
