package de.joesaxo.library.plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static de.joesaxo.library.plugin.PluginLoader.*;

public class PluginManager<C> extends GenericPluginManager<C> {

	AnnotationModule classAnnotation;

    public PluginManager(AnnotationModule pluginAnnotation, Class<C> type) {
        super(type);
        classAnnotation = pluginAnnotation;
    }

    public PluginManager(Class<? extends Annotation> annotation, Class<C> type) {
        super(type);
        classAnnotation = new AnnotationModule(annotation);
    }

    public PluginManager(Class<C> type) {
        super(type);
    }

	@Override
	protected Class<C>[] filterClasses(Class<?>[] classes) {
	    Class<C>[] filteredClasses = super.filterClasses(classes);
	    if (classAnnotation != null) filteredClasses = classFilter(filteredClasses, classAnnotation);
	    return filteredClasses;
	}

    public void callMethod(int index, AnnotationModule annotationModule, Object[] parameters) {
        C object = getLoadedPlugins()[index];
        for (Method method : object.getClass().getDeclaredMethods()) {
            Annotation annotation = getAnnotationFromModule(method, annotationModule);
            if (annotation != null) {
                if (annotationMatchesModule(annotation, annotationModule)) {
                    if (isInvokableMethod(method, parameters)) {
                        invokeMethod(method, object, parameters);
                    }
                }
            }
        }
    }

    public void callMethod(int index, AnnotationModule annotationModule, Object parameter) {
        callMethod(index, annotationModule, new Object[]{parameter});
    }

    public void callMethod(AnnotationModule annotationModule, Object[] parameters) {
        for (int i = 0; i < getLoadedPlugins().length; i++) {
            callMethod(i, annotationModule, parameters);
        }
    }

    public void callMethod(AnnotationModule annotationModule, Object parameter) {
        callMethod(annotationModule, new Object[]{parameter});
    }

    public List<Object> getPluginMethodData(int index, String name, AnnotationModule annotationModule) {
        List<Object> objectList = new ArrayList<>();
        C object = getLoadedPlugins()[index];
        for (Method method : object.getClass().getDeclaredMethods()) {
            Annotation annotation = getAnnotationFromModule(method, annotationModule);
            if (annotation != null) {
                objectList.add(getDataFromAnnotation(annotation, name));
            }
        }
        return objectList;
    }

    public List<List<Object>> getPluginMethodData(String name, AnnotationModule annotationModule) {
        List<List<Object>> objectList = new ArrayList<>();
        for (int i = 0; i < getLoadedPlugins().length; i++) {
            objectList.add(getPluginMethodData(i, name, annotationModule));
        }
        return objectList;
    }

    public Object getPluginData(int index, String name) {
        Annotation annotation = getAnnotationFromModule(getLoadedPlugins()[index].getClass(), classAnnotation);
        Method[] methods = getMethodsFromAnnotation(annotation);
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return invokeMethod(method, annotation);
            }
        }
        return null;
    }

    public Object[] getPluginData(String name) {
        Object[] objects = new Object[getLoadedPlugins().length];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = getPluginData(i, name);
        }
        return objects;
    }
}
