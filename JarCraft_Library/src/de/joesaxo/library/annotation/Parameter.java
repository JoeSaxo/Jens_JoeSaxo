package de.joesaxo.library.annotation;

import java.lang.annotation.Annotation;
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
    public Parameter(Object value) {
        method = "value";
        this.value = value;
    }

    public Parameter(String method, Annotation annotation) {
        this.method = method;
        value = getValueFromAnnotation(annotation, method);
    }

    private static Object getValueFromAnnotation(Annotation annotation, String name) {
        Method[] methods = MethodHandler.getMethodsFromAnnotation(annotation);
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return getValue(method, annotation);
            }
        }
        return null;
    }

    public String getMethod() {
        return method;
    }

    public Object getValue() {
        return value;
    }

    boolean matches(Annotation annotation) {
        return getValue().equals(getValueFromAnnotation(annotation, getMethod()));
    }



    private static Object getValue(Method annotationMethod, Annotation annotation) {
        return MethodHandler.invokeMethod(annotationMethod, annotation);
    }
}
