package de.joesaxo.library.annotation;

import de.joesaxo.library.array.Array;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Jens on 17.04.2017.
 */
public class AnnotationHandler {



    public static Annotation getAnnotationObject(Annotation[] annotations, Module annotationModule) {
        for (Annotation annotation : annotations) {
            if (annotationModule.matches(annotation)) {
                return annotation;
            }
        }
        return null;
    }

    public static Object[] filterAnnotatedObjects(Object[] classes, Module filterModule) {
        Object[] newClasses = Arrays.copyOf(classes, classes.length);
        for (int i = 0; i < newClasses.length; i++) {
            if (filterModule.getAnnotationObject(newClasses[i].getClass()) == null) newClasses[i] = null;
        }
        newClasses = Array.removeEmptyLines(newClasses);
        if (newClasses  == null) return new Object[0];
        return newClasses;
    }

    public static Method[] filterAnnotatedMethods(Method[] methods, Module filterModule) {
        Method[] newMethods = Arrays.copyOf(methods, methods.length);
        for (int i = 0; i < newMethods.length; i++) {
            if (filterModule.getAnnotationObject(newMethods[i]) == null) newMethods[i] = null;
        }
        newMethods = Array.removeEmptyLines(newMethods);
        if (newMethods == null) return new Method[0];
        return newMethods;
    }
}
