package de.joesaxo.library.annotation;

import de.joesaxo.library.array.Array;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Jens on 17.04.2017.
 */
public class AnnotationManagerOLD {
/*
    Module classFilterModule;
    Object[] classes;

    public AnnotationManagerOLD() {
        classes = new Object[0];
    }

    public AnnotationManagerOLD(Object cls) {
        setClass(cls);
    }

    public AnnotationManagerOLD(Object[] classes) {
        setClasses(classes);
    }

    public AnnotationManagerOLD(Module classFilterModule) {
        this.classFilterModule = classFilterModule;
        classes = new Object[0];
    }

    public AnnotationManagerOLD(Module classFilterModule, Object cls) {
        this.classFilterModule = classFilterModule;
        setClass(cls);
    }

    public AnnotationManagerOLD(Module classFilterModule, Object[] classes) {
        this.classFilterModule = classFilterModule;
        setClasses(classes);
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

    public Object[] invoke(Module methodModule, Module classModule, Object[] parameters, boolean returnValue) {
        Object[] classes = this.classes;
        if (classModule != null) {
            classes = classModule.filterAnnotatedObjects(this.classes);
        }
        Object[] methodBuffer = null;
        for (Object instance : classes) {
            Class<?> cls = instance.getClass();
            while (!cls.equals(Object.class)) {
                Method[] methods = methodModule.filterAnnotatedMethods(cls.getDeclaredMethods());
                methods = MethodHandler.getInvocableMethods(methods, parameters);
                Object[] methodReturns = new Object[methods.length];
                for (int i = 0; i < methods.length; i++) {
                    methodReturns[i] = MethodHandler.invokeMethod((Method)methods[i], instance, parameters);
                }
                if (methodBuffer == null) {
                    methodBuffer = methodReturns;
                } else {
                    methodBuffer = Array.addToArray(methodBuffer, methodReturns);
                }
                cls = cls.getSuperclass();
            }
        }
        return methodBuffer;
    }
    public Object[] invokeMethods(Module methodModule, Module classModule, Object[] parameters) {
        return invoke(methodModule, classModule, parameters, false);
    }

    public Object[] invokeMethods(Module methodModule, Module classModule, Object parameters) {
        return invokeMethods(methodModule, classModule, new Object[]{parameters});
    }

    public Object[] invokeMethods(Module methodModule, Module classModule) {
        return invokeMethods(methodModule, classModule, new Object[0]);
    }

    public Object[] invokeMethods(Module methodModule, Object[] parameters) {
        return invokeMethods(methodModule, classFilterModule, parameters);
    }

    public Object[] invokeMethods(Module methodModule, Object parameter) {
        return invokeMethods(methodModule, new Object[]{parameter});
    }

    public Object[] invokeMethods(Module methodModule) {
        return invokeMethods(methodModule, new Object[0]);
    }

    public Object getClassData(Object cls, String name) {
        if (classFilterModule == null) return null;
        Annotation annotation = classFilterModule.getAnnotationObject(cls.getClass());
        if (annotation==null) return null;
        return getValueFromAnnotation(annotation, name);
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
                objectData[i] = getValueFromAnnotation(annotation, name);
            }
        }
        return Array.removeEmptyLines(objectData);
    }


    static Object getValueFromAnnotation(Annotation annotation, String name) {
        Method[] methods = MethodHandler.getMethodsFromAnnotation(annotation);
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return MethodHandler.invokeMethod(method, annotation);
            }
        }
        return null;
    }

    static Parameter[] getParametersFromAnnotation(Annotation annotation) {
        Method[] methods = MethodHandler.getMethodsFromAnnotation(annotation);
        Parameter[] parameters = new Parameter[methods.length];
        for (Method method : methods) {
            new Parameter(method.getName(), MethodHandler.invokeMethod(method, annotation));
        }
        return parameters;
    }
    //*/
}
