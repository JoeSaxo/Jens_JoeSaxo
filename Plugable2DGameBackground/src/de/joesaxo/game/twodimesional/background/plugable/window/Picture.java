package de.joesaxo.game.twodimesional.background.plugable.window;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventHandler;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.PaintEvent;
import org.jarcraft.library.ImageTool;
import org.jarcraft.library.matrix.Field;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jens on 07.05.2017.
 */
public class Picture extends Component {

    private BufferedImage img;

    private String title;

    public Picture(DrawFrame frame, BufferedImage img, int x, int y) {
        super(frame, x, y, img.getWidth(), img.getHeight());
        this.img = ImageTool.copy(img);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Picture(DrawFrame frame, BufferedImage img) {
        this(frame, img, 0, 0);
    }

    public BufferedImage getImage() {
        return img;
    }

    public void draw(PaintEvent e) {
        DrawFrame frame = e.getDrawFrame();
        frame.draw(getImage(), getX(), getY());
        if (title != null) drawTitle(frame);
    }

    private void drawTitle(DrawFrame frame) {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 38);
        Field bounds = frame.getDrawStringBounds(title, font);
        //frame.drawRect((int)(getX() + (getWidth() / 2) - (bounds.getWidth()/2)), (int)(getY() + (getHeight()/2)  - (bounds.getHeight()/2)), (int)bounds.getWidth(), (int)bounds.getHeight(), Color.BLUE);
        frame.drawString(title, (int)(getX() + (getWidth() / 2) - (bounds.getWidth()/2)), (int)(getY() + (getHeight()/2)  + (bounds.getHeight()/2)), font);

    }

    public String getTitle() {
        return title;
    }
}
