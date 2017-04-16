package de.joesaxo.library.annotation;

import de.joesaxo.library.array.MethodArray;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jens on 16.04.2017.
 */
public class Parameter {

    String method;
    Object value;

    public Parameter(String method, Object value) {
        this.method = method;
        this.value = value;
    }

    public Parameter(String method, Annotation annotation) {
        this.method = method;
        this.value = getValueFromAnnotation(annotation, method);
    }

    public String getMethod() {
        return method;
    }

    public Object getValue() {
        return value;
    }

    boolean matches(Annotation annotation) {
        return value.equals(getValueFromAnnotation(annotation, method));
    }

    public static Method[] getMethodsFromAnnotation(Annotation annotation) {
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        return MethodArray.removeObjectMethods(methods);
    }

    public static Object getValueFromAnnotation(Annotation annotation, String name) {
        Method[] methods = getMethodsFromAnnotation(annotation);
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return getValue(method, annotation);
            }
        }
        return null;
    }

    private static Object getValue(Method annotationMethod, Annotation annotation) {
        try {
            return annotationMethod.invoke(annotation, new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
