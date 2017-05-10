package de.joesaxo.game.twodimesional.background.plugable;

import de.lgbk.alich.jens.Array;

import java.awt.image.BufferedImage;

/**
 * Created by Jens on 05.05.2017.
 */
public class ArrayLayer extends Array<BufferedImage> {

    public ArrayLayer(int length) {
        super(length);
    }

    @Override
    public void fill(int upperLimit) {return;}

    @Override
    public String printElement(BufferedImage element) {return null;}

    @Override
    public boolean smallerAs(BufferedImage elementOne, BufferedImage elementTwo) {
        return elementOne.getWidth() < elementTwo.getWidth();
    }

    @Override
    public boolean objectEquals(BufferedImage elementOne, BufferedImage elementTwo) {
        return elementOne.getWidth() == elementTwo.getWidth();
    }
}
