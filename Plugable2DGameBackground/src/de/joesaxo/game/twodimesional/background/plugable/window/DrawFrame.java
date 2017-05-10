package de.joesaxo.game.twodimesional.background.plugable.window;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.Event;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventHandler;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.IEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.PaintEvent;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.WindowStateEvent;
import org.jarcraft.library.matrix.Field;

import javax.swing.JLabel;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.TICK_EVENT;


/**
 * Created by Jens on 07.05.2017.
 */
public class DrawFrame extends JLabel {

    public DrawFrame() {
        EventHandler.add(this);
    }

    @Event(TICK_EVENT)
    @Override
    public void repaint() {
        super.repaint();
    }

    private WindowState windowState;


    public void changeWindowState(WindowState newWindowState) {
        if (windowState == newWindowState) return;
        IEvent e = new WindowStateEvent(windowState, newWindowState, this);
        windowState = newWindowState;
        EventHandler.invoke(e);
    }

    public WindowState getWindowState() {
        return windowState;
    }

    private Graphics graphics;

    private double widthFactor;
    private double heightFactor;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        graphics = g;
        widthFactor = 1;
        heightFactor = 1;
        EventHandler.invoke(new PaintEvent(g, this));
        graphics = null;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public void setRelativeBounds(int width, int height) {
        createFactors(width, height);
    }

    public void setRelativeBounds(BufferedImage img) {
        createFactors(img.getWidth(), img.getHeight());
    }

    public void draw(BufferedImage img, int x, int y) {
        graphics.drawImage(img, convertToAbsoluteX(x), convertToAbsoluteY(y), convertToAbsoluteX(img.getWidth()), convertToAbsoluteY(img.getHeight()), null);
    }

    public void drawString(String string, int x, int y, Font font) {
        font = new Font(font.getName(), font.getStyle(), convertToAbsolute(font.getSize()));
        Font bufferFont = graphics.getFont();
        graphics.setFont(font);
        graphics.drawString(string, convertToAbsoluteX(x), convertToAbsoluteY(y));
        graphics.setFont(bufferFont);
    }

    public Field getDrawStringBounds(String string, Font font) {
        font = new Font(font.getName(), font.getStyle(), convertToAbsolute(font.getSize()));
        Font bufferFont = graphics.getFont();
        graphics.setFont(font);
        Rectangle2D bounds;
        int width = (int)graphics.getFontMetrics().getStringBounds(string, null).getWidth();
        int height = graphics.getFontMetrics().getAscent() - graphics.getFontMetrics().getDescent();
        //font.createGlyphVector(graphics.getFontMetrics().getFontRenderContext(), string).getVisualBounds();
        //Rectangle2D bounds = getBounds()/**/;
        graphics.setFont(bufferFont);
        return new Field(convertToRelativeX(width), convertToRelativeY(height));
    }

    public Rectangle2D getBounds(String string) {

        FontRenderContext frc = ((Graphics2D)graphics).getFontRenderContext();
        GlyphVector gv = graphics.getFont().createGlyphVector(frc, string);
        Rectangle2D bounds = gv.getPixelBounds(null, 0, 0);
        return bounds;
        //return gv.;
    }

    /*
    public Font createDefaultFont(int size) {
        new Font(new Font("" ,0, 0).getAttributes())
        return new Font("SansSerif", Font.PLAIN, convertToAbsolute(size));
    }//*/

    /*
    public Font createDefaultFont() {
        return createDefaultFont(18);
    }//*/

    public void drawRect(int x, int y, int width, int height, Color color, int pixels) {
        while (pixels-- > 0) {
            Color colorBuffer = graphics.getColor();
            graphics.setColor(color);
            graphics.drawRect((int) (x * widthFactor) + pixels, (int) (y * heightFactor) + pixels, (int) (width * widthFactor) - (2 * pixels), (int) (height * heightFactor) - (2 * pixels));
            graphics.setColor(colorBuffer);
        }
    }

    public void drawRect(int x, int y, int width, int height, Color color) {
        drawRect(x, y, width, height, color, 1);
    }

    private void createFactors(int width, int height) {
        widthFactor = (double)getWidth() / (double)width;
        heightFactor = (double)getHeight() / (double)height;
    }

    public int getMousePositionX() {
        return  (int)((double)EventMouseListener.getMousePositionX() / widthFactor);
    }

    public int getMousePositionY() {
        return (int)((double)EventMouseListener.getMousePositionY()/ heightFactor);
    }

    public int convertToAbsoluteX(int relativeX) {
        return (int)(relativeX * widthFactor);
    }

    public int convertToAbsoluteY(int relativeY) {
        return (int)(relativeY * heightFactor);
    }

    public int convertToRelativeX(int absoluteX) {
        return (int)((double)absoluteX / widthFactor);
    }

    public int convertToRelativeY(int absoluteY) {
        return (int)((double)absoluteY / heightFactor);
    }

    public int convertToAbsolute(int relativeInt) {
        if (widthFactor < heightFactor) {
            return convertToAbsoluteX(relativeInt);
        } else {
            return convertToAbsoluteY(relativeInt);
        }
    }
}
