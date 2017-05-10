package de.joesaxo.library.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Jens on 29.04.2017.
 */
public abstract class IAnnotationFilter {

    public abstract boolean acceptAnnotation(Annotation annotation);


    public <T> Class<T>[] filterClasses(Class<T>[] classes) {
        int clsCounter = 0;
        for (Class<T> cls : classes) {
            if (classMatchesFilter(cls)) clsCounter++;
        }
        Class<T>[] filteredClasses = (Class<T>[])new Class<?>[clsCounter];
        for (Class<T> cls : classes) {
            if (classMatchesFilter(cls)) {
                filteredClasses[--clsCounter] = cls;
            }
        }
        return filteredClasses;
    }
    public Object[] filterClassInstances(Object[] classes) {
        int clsCounter = 0;
        for (Object cls : classes) {
            if (classMatchesFilter(cls.getClass())) clsCounter++;
        }
        Object[] filteredClasses = new Object[clsCounter];
        for (Object cls : classes) {
            if (classMatchesFilter(cls.getClass())) {
                filteredClasses[--clsCounter] = cls;
            }
        }
        return filteredClasses;
    }

    public boolean classMatchesFilter(Class<?> cls) {
        return getAnnotationFromFilter(cls) != null;
    }

    public Annotation getAnnotationFromFilter(Class<?> cls) {
        Annotation[] annotations = cls.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (acceptAnnotation(annotation)) return annotation;
        }
        return null;
    }

    public Method[] filterMethods(Method[] methods) {
        int methodCounter = 0;
        for (Method method : methods) {
            if (methodMatchesFilter(method)) methodCounter++;
        }
        Method[] filteredMethods = new Method[methodCounter];
        for (Method method : methods) {
            if (methodMatchesFilter(method)) {
                filteredMethods[--methodCounter] = method;
            }
        }
        return filteredMethods;
    }

    public boolean methodMatchesFilter(Method method) {
        return getAnnotationFromFilter(method) != null;
    }

    public Annotation getAnnotationFromFilter(Method method) {
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (acceptAnnotation(annotation)) return annotation;
        }
        return null;
    }
}
