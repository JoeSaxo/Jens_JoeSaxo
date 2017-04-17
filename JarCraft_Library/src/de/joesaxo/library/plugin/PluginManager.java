package de.joesaxo.library.plugin;

import de.joesaxo.library.annotation.AnnotationManager;
import de.joesaxo.library.annotation.Module;
import de.joesaxo.library.annotation.Parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static de.joesaxo.library.plugin.PluginLoader.*;

public class PluginManager<C> extends GenericPluginManager<C> {

	Module classAnnotation;
	AnnotationManager annotationManager;

    public PluginManager(Module pluginAnnotation, Class<C> type) {
        super(type);
        annotationManager = new AnnotationManager(pluginAnnotation);
        classAnnotation = pluginAnnotation;
    }

    public PluginManager(Class<? extends Annotation> annotation, Class<C> type) {
        super(type);
        annotationManager = new AnnotationManager(new Module(annotation));
        classAnnotation = new Module(annotation);
    }

    public PluginManager(Class<C> type) {
        super(type);
        annotationManager = new AnnotationManager();
    }

    @Override
    public void addClasses(C[] newClasses) {
        super.addClasses(newClasses);
        annotationManager.setClasses(getLoadedPlugins());
    }

	@Override
	protected Class<C>[] filterClasses(Class<?>[] classes) {
	    Class<C>[] filteredClasses = super.filterClasses(classes);
	    if (classAnnotation != null) filteredClasses = classFilter(filteredClasses, classAnnotation);
	    return filteredClasses;
	}

    public void callMethod(int index, Module annotationModule, Object[] parameters) {
        annotationManager.invokeMethods(annotationModule, parameters);
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

    public Object[] getPluginMethodData(int index, String name, Module annotationModule) {
        return annotationManager.getPluginMethodData(index, name, annotationModule);
    }

    public Object getPluginData(int index, String name) {
        return annotationManager.getClassData(index, name);
    }

    public Object[] getPluginData(String name) {
        Object[] objects = new Object[getLoadedPlugins().length];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = getPluginData(i, name);
        }
        return objects;
    }

}
