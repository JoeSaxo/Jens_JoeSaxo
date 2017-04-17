package de.joesaxo.library.annotation;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jens on 15.04.2017.
 */
public class Module {

    private List<Parameter> parameters;

    private Class<? extends Annotation> annotation;

    public static boolean isAllowedAnnotation(Class<? extends Annotation> annotation) {
        return annotation.getAnnotation(Retention.class).value().equals(RetentionPolicy.RUNTIME);
    }

    public Module(Class<? extends Annotation> annotation) {
        if (!isAllowedAnnotation(annotation)) {
            throw new IllegalArgumentException(annotation + "must be accessible at runntime!");
        }
        this.annotation = annotation;
        parameters = new ArrayList<>();
    }

    public Module addParameter(Parameter parameter) {
        parameters.add(parameter);
        return this;
    }

    public Module addParameter(String method, Object value) {
        parameters.add(new Parameter(method, value));
        return this;
    }

    public Module addParameter(Object value) {
        parameters.add(new Parameter(value));
        return this;
    }

    public int parameters() {
        return parameters.size();
    }

    public Parameter getParameter(int index) {
        return parameters.get(index);
    }

    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    public boolean matches(Annotation annotation) {
        if (!annotation.annotationType().equals(this.annotation)) return false;
        for (int i = 0; i < this.parameters(); i++) {
            Parameter parameter = this.getParameter(i);
            if (!parameter.matches(annotation)) return false;
        }
        return true;
    }

    public Module getCopy() {
        Module copy = new Module(annotation);
        for (Parameter parameter : parameters) {
            copy.addParameter(parameter);
        }
        return copy;
    }



    public Annotation getAnnotationObject(Method method) {
        return AnnotationHandler.getAnnotationObject(method.getDeclaredAnnotations(), this);
    }

    public Annotation getAnnotationObject(Class<?> cls) {
        return AnnotationHandler.getAnnotationObject(cls.getAnnotations(), this);
    }
}