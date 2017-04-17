package de.joesaxo.library.array;

import java.lang.reflect.Method;
import java.util.Arrays;

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
        if (empty == 0) return array;
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

    public static <T> T[] addToArray(T[] oldArray, T[] newElements) {
        T[] array = Arrays.copyOf(oldArray, oldArray.length + newElements.length);
        for (int i = 0; i < newElements.length; i++) {
            array[oldArray.length + i] = newElements[i];
        }
        return array;
    }
}
