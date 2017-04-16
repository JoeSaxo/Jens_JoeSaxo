package de.joesaxo.library.annotation;

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
        for (int i = 0; i < this.parameters(); i++) {
            Parameter parameter = this.getParameter(i);
            if (!parameter.matches(annotation)) return false;
        }
        return true;
    }


    private Annotation getAnnotationObject(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(this.annotation)) {
                if (this.matches(annotation)) {
                    return annotation;
                }
            }
        }
        return null;
    }

    public Annotation getAnnotationObject(Method method) {
        return getAnnotationObject(method.getDeclaredAnnotations());
    }

    public Annotation getAnnotationObject(Class<?> cls) {
        return getAnnotationObject(cls.getAnnotations());
    }
}