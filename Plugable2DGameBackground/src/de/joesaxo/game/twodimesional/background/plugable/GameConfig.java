package de.joesaxo.game.twodimesional.background.plugable;

/**
 * Created by Jens on 06.05.2017.
 */
public class GameConfig {

    private int windowHeight;

    private int windowWidth;

    public void load() {
        windowWidth = 400;
        windowHeight = 150;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowWidth() {
        return windowWidth;
    }
}
