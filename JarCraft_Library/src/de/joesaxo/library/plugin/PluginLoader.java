package de.joesaxo.library.plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jens on 15.04.2017.
 */
public class PluginLoader extends GenericPluginLoader {

    public static Object invokeMethod(Method method, Object object, Object[] parameters) {
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invokeMethod(Method method, Object object) {
        return invokeMethod(method, object, new Object[]{});
    }

    public static boolean isInvokableMethod(Method method, Object[] parameters) {
        Type[] neededParameters = method.getGenericParameterTypes();
        if (neededParameters.length != parameters.length) return false;

        for (int i = 0; i < parameters.length; i++) {
            if (!parameters[i].getClass().equals(neededParameters[i])) return false;
        }
        return true;
    }

    public static boolean isObjetctMethod(Method method) {
        Method[] methodsOfClassObject = Object.class.getDeclaredMethods();
        for (Method objectMethod : methodsOfClassObject) {
            if (method.equals(objectMethod)) return true;
        }
        return false;
    }

    public static <A  extends Annotation> A getAnnotationFromModule(Method method, AnnotationModule annotationModule) {
        A annotation = getAnnotationFromClass(method, annotationModule.getAnnotation());
        if (annotation != null) {
            if (annotationMatchesModule(annotation, annotationModule)) {
                return annotation;
            }
        }
        return null;
    }

    public static <A  extends Annotation> A getAnnotationFromModule(Class<?> cls, AnnotationModule annotationModule) {
        A annotation = getAnnotationFromClass(cls, annotationModule.getAnnotation());
        if (annotation != null) {
            if (annotationMatchesModule(annotation, annotationModule)) {
                return annotation;
            }
        }
        return null;
    }

    public static <A  extends Annotation> A getAnnotationFromClass(Method method, Class<? extends Annotation> annotationClass) {
        return getAnnotationFromClass(method.getDeclaredAnnotations(), annotationClass);
    }

    public static <A  extends Annotation> A getAnnotationFromClass(Class<?> cls, Class<? extends Annotation> annotationClass) {
        return getAnnotationFromClass(cls.getAnnotations(), annotationClass);
    }

    private static <A  extends Annotation> A getAnnotationFromClass(Annotation[] annotations, Class<? extends Annotation> annotationClass) {
        for (Annotation methodAnnotation : annotations) {
            if (methodAnnotation.annotationType().equals(annotationClass)) {
                return (A)methodAnnotation;
            }
        }
        return null;
    }



    public static boolean annotationMatchesModule(Annotation annotation, AnnotationModule parameters) {
        for (int i = 0; i < parameters.parameters(); i++) {
            String name = parameters.getParameterName(i);
            Object value = parameters.getParameterValue(i);
            if (!annotationMatchesParameter(annotation, name, value)) return false;
        }
        return true;
    }

    public static boolean annotationMatchesParameter(Annotation annotation, String name, Object value) {
        for (Method method : getMethodsFromAnnotation(annotation)) {
                if (method.getName().equals(name)) {
                    if (value.equals(invokeMethod(method, annotation))) return true;
                }
        }
        return false;
    }

    public static Object getDataFromAnnotation(Annotation annotation, String name) {
        Method[] methods = getMethodsFromAnnotation(annotation);
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return invokeMethod(method, annotation);
            }
        }
        return null;
    }

    public static Method[] getMethodsFromAnnotation(Annotation annotation) {
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        return removeObjectMethods(methods);
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

    public static <C> Class<C>[] classFilter(Class<C>[] classes, AnnotationModule annotationParrameter) {
        Class<C>[] annotatedClasses = (Class<C>[])new Class<?>[classes.length];
        for (int i = 0; i < classes.length; i++) {
            if (getAnnotationFromModule(classes[i], annotationParrameter) != null) {
                annotatedClasses[i] = GenericPluginLoader.convert(classes[i]);
            }
        }
        return removeEmpty(annotatedClasses);
    }

}
