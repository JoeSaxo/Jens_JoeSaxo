package de.joesaxo.library.plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import static de.joesaxo.library.plugin.AdvancedPluginLoader.*;

public class AdvancedPluginManager <C> extends PluginManager <C>{

	AnnotationModule classAnnotation;

    public AdvancedPluginManager(AnnotationModule pluginAnnotation, Class<C> type) {
        super(type);
        classAnnotation = pluginAnnotation;
    }

    public AdvancedPluginManager(Class<? extends Annotation> annotation, Class<C> type) {
        super(type);
        classAnnotation = new AnnotationModule(annotation);
    }

    public AdvancedPluginManager(Class<C> type) {
        super(type);
    }

	@Override
	protected List<Class<C>> filterClasses(List<Class<?>> classes) {
	    List<Class<C>> filteredClasses = super.filterClasses(classes);
	    if (classAnnotation != null) filteredClasses = classFilter(filteredClasses, classAnnotation);
	    return filteredClasses;
	}

    public void callMethod(int index, AnnotationModule annotationModule, Object[] parameters) {
        C object = getLoadedPlugins().get(index);
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

    public void callMethod(int index, Class<? extends Annotation> annotation, Object[] parameters) {
        callMethod(index, new AnnotationModule(annotation), parameters);
    }

    public void callMethod(int index, AnnotationModule annotationModule, Object parameter) {
        callMethod(index, annotationModule, new Object[]{parameter});
    }

    public void callMethod(int index, Class<? extends Annotation> annotation, Object parameter) {
        callMethod(index, new AnnotationModule(annotation), new Object[]{parameter});
    }

    public void callMethod(AnnotationModule annotationModule, Object[] parameters) {
        for (int i = 0; i < getLoadedPlugins().size(); i++) {
            callMethod(i, annotationModule, parameters);
        }
    }

    public void callMethod(Class<? extends Annotation> annotation, Object[] parameters) {
        callMethod(new AnnotationModule(annotation), parameters);
    }

    public void callMethod(AnnotationModule annotationModule, Object parameter) {
        callMethod(annotationModule, new Object[]{parameter});
    }

    public void callMethod(Class<? extends Annotation> annotation, Object parameter) {
        callMethod(new AnnotationModule(annotation), new Object[]{parameter});
    }

    public Object getPluginData(int index, String name) {
        Annotation annotation = getAnnotationFromModule(getLoadedPlugins().get(index).getClass(), classAnnotation);
        Method[] methods = getMethodsFromAnnotation(annotation);
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return invokeMethod(method, annotation);
            }
        }
        return null;
    }

    public Object[] getPluginData(String name) {
        Object[] objects = new Object[getLoadedPlugins().size()];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = getPluginData(i, name);
        }
        return objects;
    }
}
