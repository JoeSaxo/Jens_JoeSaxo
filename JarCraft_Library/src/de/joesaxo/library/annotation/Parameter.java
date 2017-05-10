package de.joesaxo.library.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Jens on 16.04.2017.
 */
public class Parameter {

    String methodName;
    Object value;

    public Parameter(String methodName, Object value) {
        this.methodName = methodName;
        this.value = value;
    }

    public Parameter(String methodName) {
        this.methodName = methodName;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object generateValueFromAnnotation(Annotation annotation) {
        value = generateValueFromAnnotation(annotation, methodName);
        return value;
    }

    public static Object generateValueFromAnnotation(Annotation annotation, String name) {
        Method[] methods = MethodHandler.getMethodsFromAnnotation(annotation);
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return MethodHandler.invokeMethod(method, annotation);
            }
        }
        return null;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object getValue() {
        return value;
    }

    boolean matches(Annotation annotation) {
        if (getValue() == null) {
            return generateValueFromAnnotation(annotation, getMethodName()) == null;
        }
        return getValue().equals(generateValueFromAnnotation(annotation, getMethodName()));
    }

    public boolean matches(Parameter parameter) {
        if (parameter.getValue() == null) {
            return value == null;
        }
        return (parameter.getMethodName().equals(methodName) && parameter.getValue().equals(value));
    }
}
