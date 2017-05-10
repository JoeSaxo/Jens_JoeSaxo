package org.jarcraft.library.matrix;

/**
 * Created by Jens on 09.05.2017.
 */
public class Field extends Point {

    int width, height;

    public Field(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
