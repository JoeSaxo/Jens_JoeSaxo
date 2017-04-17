package de.joesaxo.library.annotation;

import de.joesaxo.library.array.Array;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jens on 17.04.2017.
 */
public class AnnotationManager {

    Module classFilterModule;
    Object[] classes;

    public AnnotationManager() {
        classes = new Object[0];
    }

    public AnnotationManager(Object cls) {
        setClass(cls);
    }

    public AnnotationManager(Object[] classes) {
        setClasses(classes);
    }

    public AnnotationManager(Module classFilterModule) {
        this.classFilterModule = classFilterModule;
        classes = new Object[0];
    }

    public AnnotationManager(Module classFilterModule, Object cls) {
        this.classFilterModule = classFilterModule;
        setClass(cls);
    }

    public AnnotationManager(Module classFilterModule, Object[] classes) {
        this.classFilterModule = classFilterModule;
        setClasses(classes);
    }

    @Deprecated
    public void addClasses(Object[] classes) {
        Object[] copyOfClasses = Arrays.copyOf(classes, classes.length);
        for (int i = 0; i < classes.length; i++) {
            if (classFilterModule != null && classFilterModule.getAnnotationObject(copyOfClasses[i].getClass()) == null) copyOfClasses[i] = null;
        }
        this.classes = Array.addToArray(this.classes, Array.removeEmptyLines(copyOfClasses));
    }

    @Deprecated
    public void addClass(Object cls) {
        addClasses(new Object[]{cls});
    }

    public void clearClasses() {
        classes = new Object[0];
    }

    public boolean setClass(Object cls) {
        return setClasses(new Object[]{cls});
    }

    public boolean setClasses(Object[] classes) {
        if (classFilterModule != null) {
            this.classes = classFilterModule.filterAnnotatedObjects(classes);
        } else {
            this.classes = classes;
        }
        return this.classes.length > 0;
    }

    public Object[] getClasses() {
        return classes;
    }

    public void invokeMethods(Module methodModule, Module classModule, Object[] parameters) {
        Object[] classes = this.classes;
        if (classModule != null) {
            classes = classModule.filterAnnotatedObjects(this.classes);
        }
        for (Object cls : classes) {
            for (Method method : methodModule.filterAnnotatedMethods(cls.getClass().getDeclaredMethods())) {
                if (MethodHandler.isInvokableMethod(method, parameters)) {
                    MethodHandler.invokeMethod(method, cls, parameters);
                }
            }
        }
    }

    public void invokeMethods(Module methodModule, Module classModule, Object parameters) {
        invokeMethods(methodModule, classModule, new Object[]{parameters});
    }

    public void invokeMethods(Module methodModule, Module classModule) {
        invokeMethods(methodModule, classModule, new Object[0]);
    }

    public void invokeMethods(Module methodModule, Object[] parameters) {
        invokeMethods(methodModule, classFilterModule, parameters);
    }

    public void invokeMethods(Module methodModule, Object parameter) {
        invokeMethods(methodModule, new Object[]{parameter});
    }

    public void invokeMethods(Module methodModule) {
        invokeMethods(methodModule, new Object[0]);
    }

    public Object getClassData(Object cls, String name) {
        if (classFilterModule == null) return null;
        Annotation annotation = classFilterModule.getAnnotationObject(cls.getClass());
        if (annotation==null) return null;
        return new Parameter(name, annotation).getValue();
    }

    public Object getClassData(int index, String name) {
        return getClassData(classes[index], name);
    }

    public Object[] getClassData(String name) {
        Object[] objects = new Object[classes.length];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = getClassData(i, name);
        }
        return objects;
    }

    public Object[] getPluginMethodData(int index, String name, Module methodModule) {
        Object object = classes[index];
        Method[] methods = object.getClass().getDeclaredMethods();
        Object[] objectData = new Object[methods.length];
        for (int i = 0; i < methods.length; i++) {
            Annotation annotation = methodModule.getAnnotationObject(methods[i]);
            if (annotation != null) {
                objectData[i] = new Parameter(name, annotation).getValue();
            }
        }
        return Array.removeEmptyLines(objectData);
    }
}
