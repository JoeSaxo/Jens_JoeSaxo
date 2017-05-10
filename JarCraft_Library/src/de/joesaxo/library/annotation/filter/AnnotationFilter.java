package de.joesaxo.library.annotation.filter;

import de.joesaxo.library.annotation.IAnnotationFilter;
import de.joesaxo.library.annotation.MethodHandler;
import de.joesaxo.library.annotation.Parameter;
import de.joesaxo.library.array.Array;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

/**
 * Created by Jens on 29.04.2017.
 */
public class AnnotationFilter extends IAnnotationFilter {

    Class<? extends Annotation> annotation;

    Parameter[] parameters;

    IAnnotationFilter annotationFilter;

    public static boolean isAllowedAnnotation(Class<? extends Annotation> annotation) {
        return annotation.getAnnotation(Retention.class).value().equals(RetentionPolicy.RUNTIME);
    }

    public AnnotationFilter() {};

    public AnnotationFilter(Class<? extends Annotation> annotation) {
        if (!isAllowedAnnotation(annotation)) {
            throw new IllegalArgumentException(annotation + "must be accessible at runtime!");
        }
        this.annotation = annotation;
    }

    public AnnotationFilter(Class<? extends Annotation> annotation, Parameter[] parameters) {
        this(annotation);
        this.parameters = new Parameter[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            this.parameters[i] = parameters[i];
        }
    }

    public AnnotationFilter(Class<? extends Annotation> annotation, Parameter parameter) {
        this(annotation, new Parameter[]{parameter});
    }

    public AnnotationFilter setAnnotation(Class<? extends Annotation> annotation) {
        if (!isAllowedAnnotation(annotation)) {
            throw new IllegalArgumentException(annotation + "must be accessible at runtime!");
        }
        this.annotation = annotation;
        return this;
    }

    public AnnotationFilter setParameters(Parameter[] parameters) {
        this.parameters = new Parameter[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            this.parameters[i] = parameters[i];
        }
        return this;
    }

    public AnnotationFilter setParameter(Parameter parameter) {
        parameters = new Parameter[]{parameter};
        return this;
    }

    public Parameter[] getParameters() {
        Parameter[] parameters = new Parameter[this.parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = this.parameters[i];
        }
        return parameters;
    }

    public AnnotationFilter addParameter(Parameter parameter) {
        addParameters(new Parameter[]{parameter});
        return this;
    }

    public AnnotationFilter addParameters(Parameter[] parameters) {
        if (this.parameters == null) {
            setParameters(parameters);
        } else {
            this.parameters = Array.addToArray(this.parameters, parameters);
        }
        return this;
    }

    public AnnotationFilter setAnnotationFilter(IAnnotationFilter annotationFilter) {
        this.annotationFilter = annotationFilter;
        return this;
    }

    @Override
    public boolean acceptAnnotation(Annotation annotation) {
        if (this.annotation != null && !annotation.annotationType().equals(this.annotation)) return false;
        if (parameters == null) return annotationFilter == null || annotationFilter.acceptAnnotation(annotation);
        for (int i = 0; i < parameters.length; i++) {
            for (Parameter parameter : getParametersFromAnnotation(annotation)) {
                if (parameter.getMethodName().equals(parameters[i].getMethodName()) && !parameter.matches(parameters[i])) {
                    return false;
                }
            }
        }
        return annotationFilter == null || annotationFilter.acceptAnnotation(annotation);
    }

    private Parameter[] getParametersFromAnnotation(Annotation annotation) {
        Method[] methods = MethodHandler.getMethodsFromAnnotation(annotation);
        Parameter[] parameters = new Parameter[methods.length];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = new Parameter(methods[i].getName(), MethodHandler.invokeMethod(methods[i], annotation));
        }
        return parameters;
    }


}
