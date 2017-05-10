package de.joesaxo.game.twodimesional.background.plugable.window;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.Event;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventHandler;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.ButtonEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.MouseEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.PaintEvent;
import org.jarcraft.library.matrix.Field;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.BUTTON_CLICK_EVENT;

/**
 * Created by Jens on 07.05.2017.
 */
public class Button extends Picture{

    private Color borderColor;

    private int borderWidth;

    public Button(DrawFrame frame, BufferedImage img, int x, int y, String name) {
        super(frame, img, x, y);
        EventHandler.add(this);
        borderColor = Color.BLACK;
        borderWidth = 5;
        setTitle(name);
    }

    public Button(DrawFrame frame, BufferedImage img, String name) {
        this(frame, img, 0, 0, name);
    }

    @Override
    public void draw(PaintEvent e) {
        super.draw(e);
        //DrawFrame frame = e.getDrawFrame();
        //frame.drawString("Hello World", (int)(getX() + (getWidth() / 2) - (bounds.getWidth()/2)), (int)(getY() + ((getHeight()  + bounds.getHeight())/2)), font);
        //frame.drawString("HelloWorld", getX(), getY()+(int)((18.0/3.0*2.0)/2.0) + getHeight() / 2);

        if (mouseOver()) {
            drawBorder(e.getDrawFrame());
        }
    }

    public void drawBorder(DrawFrame frame) {
        frame.drawRect(getX(), getY(), getWidth(), getHeight(), borderColor, borderWidth);
    }

    /*
    @Override
    public boolean mouseOver() {
        if (super.mouseOver()) {
            EventHandler.invoke(new ButtonEvent(MOUSE_OVER_BUTTON_EVENT, this));
            return true;
        }
        return false;
    }//*/

    boolean clicked;

    @Event(EventType.MOUSE_PRESS_EVENT)
    public void onButtonClick(MouseEvent e) {
        if (mouseOver() && isActive() && !clicked) {
            System.out.println("BUTTON: " + getTitle());
            clicked = true;
            EventHandler.invoke(new ButtonEvent(BUTTON_CLICK_EVENT, this));
            //System.exit(0);
        }
    }

    @Event(EventType.MOUSE_RELEASE_EVENT)
    public void onButtonRelease(MouseEvent e) {
        if (mouseOver()) {
            clicked = false;
        }
    }

}
