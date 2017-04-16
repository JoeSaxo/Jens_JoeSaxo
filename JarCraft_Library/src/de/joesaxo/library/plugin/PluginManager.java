package de.joesaxo.library.plugin;

import de.joesaxo.library.annotation.Module;
import de.joesaxo.library.annotation.Parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static de.joesaxo.library.plugin.PluginLoader.*;

public class PluginManager<C> extends GenericPluginManager<C> {

	Module classAnnotation;

    public PluginManager(Module pluginAnnotation, Class<C> type) {
        super(type);
        classAnnotation = pluginAnnotation;
    }

    public PluginManager(Class<? extends Annotation> annotation, Class<C> type) {
        super(type);
        classAnnotation = new Module(annotation);
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

    public void callMethod(int index, Module annotationModule, Object[] parameters) {
        C object = getLoadedPlugins()[index];
        for (Method method : object.getClass().getDeclaredMethods()) {
            Annotation annotation = annotationModule.getAnnotationObject(method);
            if (annotation != null) {
                if (isInvokableMethod(method, parameters)) {
                    invokeMethod(method, object, parameters);
                }
            }
        }
    }

    public void callMethod(int index, Module annotationModule, Object parameter) {
        callMethod(index, annotationModule, new Object[]{parameter});
    }

    public void callMethod(int index, Module annotationModule) {
        callMethod(index, annotationModule, new Object[]{});
    }

    public void callMethod(Module annotationModule, Object[] parameters) {
        for (int i = 0; i < getLoadedPlugins().length; i++) {
            callMethod(i, annotationModule, parameters);
        }
    }

    public void callMethod(Module annotationModule, Object parameter) {
        callMethod(annotationModule, new Object[]{parameter});
    }

    public void callMethod(Module annotationModule) {
        callMethod(annotationModule, new Object[]{});
    }

    public List<Object> getPluginMethodData(int index, String name, Module annotationModule) {
        List<Object> objectList = new ArrayList<>();
        C object = getLoadedPlugins()[index];
        for (Method method : object.getClass().getDeclaredMethods()) {
            Annotation annotation = annotationModule.getAnnotationObject(method);
            if (annotation != null) {
                objectList.add(new Parameter(name, annotation).getValue());
            }
        }
        return objectList;
    }

    public List<List<Object>> getPluginMethodData(String name, Module annotationModule) {
        List<List<Object>> objectList = new ArrayList<>();
        for (int i = 0; i < getLoadedPlugins().length; i++) {
            objectList.add(getPluginMethodData(i, name, annotationModule));
        }
        return objectList;
    }

    public Object getPluginData(int index, String name) {
        Annotation annotation = classAnnotation.getAnnotationObject(getLoadedPlugins()[index].getClass());
        return new Parameter(name, annotation).getValue();
    }

    public Object[] getPluginData(String name) {
        Object[] objects = new Object[getLoadedPlugins().length];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = getPluginData(i, name);
        }
        return objects;
    }

}
