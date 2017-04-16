package de.joesaxo.library.array;

import java.lang.reflect.Method;

/**
 * Created by Jens on 16.04.2017.
 */
public class Array <T> {


    public static <T> T[] removeEmptyLines(T[] array) {
        Class<T> arrayType = null;
        int empty = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                empty++;
            } else if (arrayType == null) {
                arrayType = (Class<T>)((T)array[i]).getClass();
            }
        }
        if (arrayType == null) return null;
        int skipped = 0;
        T[] newArray = (T[]) java.lang.reflect.Array.newInstance(arrayType, array.length-empty);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                newArray[i-skipped] = array[i];
            } else {
                skipped++;
            }
        }
        return newArray;
    }


    public static <E> E[] createArray(E element, int length) {
        return (E[]) java.lang.reflect.Array.newInstance(element.getClass(), length);
    }
}
