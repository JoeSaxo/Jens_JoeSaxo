package de.joesaxo.library.plugin;

import de.joesaxo.library.annotation.Module;
import de.joesaxo.library.array.Array;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by Jens on 15.04.2017.
 */
public class PluginLoader extends GenericPluginLoader {

    public static <C> Class<C>[] classFilter(Class<C>[] classes, Module annotationParrameter) {
        Class<C>[] annotatedClasses = (Class<C>[])new Class<?>[classes.length];
        for (int i = 0; i < classes.length; i++) {
            if (annotationParrameter.getAnnotationObject(classes[i]) != null) {
                annotatedClasses[i] = GenericPluginLoader.convert(classes[i]);
            }
        }
        return Array.removeEmptyLines(annotatedClasses);
    }

}
