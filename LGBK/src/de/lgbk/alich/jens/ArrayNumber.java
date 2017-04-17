package de.lgbk.alich.jens;

import java.util.Random;

/**
 * Created by Jens on 14.04.2017.
 */
public class ArrayNumber extends Array<Integer> {

    public ArrayNumber() {
        super();
    }

    public ArrayNumber(int length) {
        super(length);
    }

    @Override
    public void fill(int upperLimit) {
        Random random = new Random();
        for (int i=0; i<getLength(); i++)
            setElement(random.nextInt(upperLimit)+1, i);
    }

    @Override
    public String printElement(Integer element) {
        return String.valueOf(element);
    }

    @Override
    public boolean smallerAs(Integer elementOne, Integer elementTwo) {
        return elementOne < elementTwo;
    }

    public static void main(String[] args) {
        ArrayNumber array = new ArrayNumber(20);
        array.fill();
        array.printArray();
        array.mergesort();
        array.printArray();
    }
}
