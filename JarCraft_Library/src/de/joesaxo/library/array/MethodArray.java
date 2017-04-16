package de.joesaxo.library.array;

import java.lang.reflect.Method;

/**
 * Created by Jens on 16.04.2017.
 */
public class MethodArray extends Array<Method> {


    public static boolean isObjetctMethod(Method method) {
        Method[] methodsOfClassObject = Object.class.getDeclaredMethods();
        for (Method objectMethod : methodsOfClassObject) {
            if (method.equals(objectMethod)) return true;
        }
        return false;
    }

    public static Method[] removeObjectMethods(Method[] methods) {
        int unneededMethods = 0;
        for (Method method : methods) {
            if (isObjetctMethod(method)) unneededMethods++;
        }
        Method[] newMethods = new Method[methods.length - unneededMethods];
        int skippedMethods = 0;
        for (int i = 0; i < methods.length; i++) {
            if (!isObjetctMethod(methods[i])) {
                newMethods[i-skippedMethods] = methods[i];
            } else {
                skippedMethods++;
            }
        }
        return newMethods;
    }

}
