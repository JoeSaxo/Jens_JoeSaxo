package de.joesaxo.library.annotation;

import de.joesaxo.library.array.Array;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jens on 15.04.2017.
 */
@Deprecated
public class ModuleOLD {
/*
    private List<Parameter> parameters;

    private IAnnotationFilter annotationHandler;

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

    private Module(Module module) {
        this.annotation = module.getAnnotation();
        parameters = new ArrayList<>();
        parameters.addAll(module.parameters);
    }

    public void setAnnotationHandler(IAnnotationFilter annotationHandler) {
        this.annotationHandler = annotationHandler;
    }

    public Module addParameterr(Parameter parameter) {
        parameters.add(parameter);
        return this;
    }

    public Module addParameterr(Parameter parameter, boolean createNewModule) {
        if (createNewModule) {
            return new Module(this).addParameterr(parameter);
        } else {
            return addParameterr(parameter);
        }
    }

    public Module addParameterr(String method, Object value, boolean createNewModule) {
        return addParameterr(new Parameter(method, value), createNewModule);
    }

    public Module addParameterr(String method, Object value) {
        return addParameterr(new Parameter(method, value));
    }

    public Module addParameterr(Object value, boolean createNewModule) {
        return addParameterr(new Parameter(value), createNewModule);
    }

    public Module addParameterr(Object value) {
        return addParameterr(new Parameter(value));
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
            if (!parameter.matches(annotation)) {
                return false;
            }
        }
        if (annotationHandler != null && !annotationHandler.acceptAnnotation(annotation)) return false;
        return true;
    }


    private Annotation getAnnotationObject(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (this.matches(annotation)) {
                return annotation;
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

    public Object[] filterAnnotatedObjects(Object[] classes) {
        Object[] newClasses = Arrays.copyOf(classes, classes.length);
        for (int i = 0; i < newClasses.length; i++) {
            if (this.getAnnotationObject(newClasses[i].getClass()) == null) newClasses[i] = null;
        }
        newClasses = Array.removeEmptyLines(newClasses);
        if (newClasses  == null) return new Object[0];
        return newClasses;
    }

    public Method[] filterAnnotatedMethods(Method[] methods) {
        Method[] newMethods = Arrays.copyOf(methods, methods.length);
        for (int i = 0; i < newMethods.length; i++) {
            Annotation annotation = this.getAnnotationObject(newMethods[i]);
            if (annotation == null) newMethods[i] = null;
        }
        newMethods = Array.removeEmptyLines(newMethods);
        if (newMethods == null) return new Method[0];
        return newMethods;
    }

//*/

}