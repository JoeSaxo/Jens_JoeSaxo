package de.joesaxo.library.annotation;

import de.joesaxo.library.annotation.filter.AnnotationFilter;
import de.joesaxo.library.array.Array;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Jens on 17.04.2017.
 */
public class AnnotationManager {

    IAnnotationFilter classFilter;
    Object[] classes;

    public AnnotationManager() {
        classes = new Object[0];
    }

    public AnnotationManager(IAnnotationFilter classFilter) {
        this.classFilter = classFilter;
        classes = new Object[0];
    }

    public void clearClasses() {
        classes = new Object[0];
    }

    public void setClass(Object cls) {
        classes = new Object[]{cls};
    }

    public boolean setClasses(Object[] classes) {
        if (classFilter != null) {
            this.classes = classFilter.filterClassInstances(classes);
        } else {
            this.classes = classes;
        }
        return this.classes.length > 0;
    }

    public Object[] getClasses() {
        return classes;
    }

    public Object[] invoke(IAnnotationFilter methodFilter, IAnnotationFilter classFilter, Object[] parameters) {
        Object[] classes = this.classes;
        if (classFilter != null) {
            classes = classFilter.filterClassInstances(classes);
        }

        Object[] methodBuffer = new Object[0];
        for (Object instance : classes) {
            methodBuffer = getClassReturns(instance.getClass(), methodFilter, instance, parameters, methodBuffer);
        }
        return methodBuffer;
    }

    private Object[] getClassReturns(Class<?> cls, IAnnotationFilter methodFilter, Object instance, Object[] parameters, Object[] methodBuffer) {

        if (cls == null) return methodBuffer;

        Object[] returnValues = getFilteredMethodReturns(cls.getDeclaredMethods(), methodFilter, instance, parameters);
        methodBuffer = Array.addToArray(methodBuffer, returnValues);

        for (Class<?> iFace : cls.getInterfaces()) {
            methodBuffer = getClassReturns(iFace, methodFilter, instance, parameters, methodBuffer);
        }

        for (Class<?> subClass : cls.getDeclaredClasses()) {
            methodBuffer = getClassReturns(subClass, methodFilter, instance, parameters, methodBuffer);
        }

        methodBuffer = getClassReturns(cls.getSuperclass(), methodFilter, instance, parameters, methodBuffer);

        return methodBuffer;
    }

    private Object[] getFilteredMethodReturns(Method[] methods, IAnnotationFilter methodFilter, Object instance, Object[] parameters) {
        methods = methodFilter.filterMethods(methods);
        methods = MethodHandler.getInvocableMethods(methods, parameters);
        Object[] methodReturns = new Object[methods.length];
        for (int i = 0; i < methods.length; i++) {
            //if (methods[i].getName() != "repaint" && methods[i].getName() != "tick" && methods[i].getName() != "stopOnWindowClose" && methods[i].getName() != "paint" && methods[i].getName() != "mouseMoved")
            //System.out.println(methods[i].getName());
            methodReturns[i] = MethodHandler.invokeMethod(methods[i], instance, parameters);
        }
        return methodReturns;
    }

    public Object[] invokeMethods(IAnnotationFilter methodFilter, IAnnotationFilter classFilter, Object[] parameters) {
        return invoke(methodFilter, classFilter, parameters);
    }

    public Object[] invokeMethods(IAnnotationFilter methodFilter, IAnnotationFilter classFilter, Object parameter) {
        return invoke(methodFilter, classFilter, new Object[]{parameter});
    }

    public Object[] invokeMethods(IAnnotationFilter methodFilter, IAnnotationFilter classFilter) {
        return invoke(methodFilter, classFilter, new Object[0]);
    }

    public Object[] invokeMethods(IAnnotationFilter methodFilter, Object[] parameters) {
        return invoke(methodFilter, classFilter, parameters);
    }

    public Object[] invokeMethods(IAnnotationFilter methodFilter, Object parameter) {
        return invoke(methodFilter, classFilter, new Object[]{parameter});
    }

    public Object[] invokeMethods(IAnnotationFilter methodFilter) {
        return invoke(methodFilter, classFilter, new Object[0]);
    }
    public Object getAnnotationValueFromClass(Object cls, Parameter parameter) {
        if (classFilter == null) return null;
        if (!classFilter.classMatchesFilter(cls.getClass())) return null;
        Annotation annotation = classFilter.getAnnotationFromFilter(cls.getClass());
        if (annotation==null) return null;
        return parameter.generateValueFromAnnotation(annotation);
    }

    public Object getAnnotationValueFromClass(int index, Parameter parameter) {
        return getAnnotationValueFromClass(classes[index], parameter);
    }

    public Object[] getAnnotationValueFromClass(String name) {
        Object[] objects = new Object[classes.length];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = getAnnotationValueFromClass(i, new Parameter(name));
        }
        return objects;
    }

    /*

    public Object getMethodData(Method method) {
        if (classFilter == null) return null;
        if (!AnnotationFilter.classMatchesFilter(cls.getClass(), classFilter)) return null;
        Annotation annotation = AnnotationFilter.getAnnotationFromFilter(cls.getClass(), classFilter);
        if (annotation==null) return null;
        return getValueFromAnnotation(annotation, name);
    }
//*/
    public Object[] getPluginMethodData(Object instance, String name, AnnotationFilter methodFilter) {
        Object object = instance;
        Method[] methods = object.getClass().getDeclaredMethods();
        Object[] objectData = new Object[methods.length];
        for (int i = 0; i < methods.length; i++) {
            Annotation annotation = methodFilter.getAnnotationFromFilter(methods[i]);
            if (annotation != null) {
                objectData[i] = new Parameter(name).generateValueFromAnnotation(annotation, name);
            }
        }
        return Array.removeEmptyLines(objectData);
    }

    public Object[] getPluginMethodData(int index, String name, AnnotationFilter methodFilter) {
        return getPluginMethodData(classes[index], name, methodFilter);
    }


/*
    static Parameter[] getParametersFromAnnotation(Annotation annotation) {
        Method[] methods = MethodHandler.getMethodsFromAnnotation(annotation);
        Parameter[] parameters = new Parameter[methods.length];
        for (Method method : methods) {
            new Parameter(method.getName(), MethodHandler.invokeMethod(method, annotation));
        }
        return parameters;
    }//*/

}
