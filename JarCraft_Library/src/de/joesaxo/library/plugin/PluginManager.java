package de.joesaxo.library.plugin;

import de.joesaxo.library.annotation.filter.AnnotationFilter;
import de.joesaxo.library.annotation.AnnotationManager;

import java.lang.annotation.Annotation;

public class PluginManager<C> extends GenericPluginManager<C> {

	AnnotationFilter classAnnotation;
	AnnotationManager annotationManager;

    public PluginManager(AnnotationFilter pluginAnnotation, Class<C> type) {
        super(type);
        annotationManager = new AnnotationManager(pluginAnnotation);
        classAnnotation = pluginAnnotation;
    }

    public PluginManager(Class<? extends Annotation> annotation, Class<C> type) {
        super(type);
        AnnotationFilter annotationFilter = new AnnotationFilter();
        annotationFilter.setAnnotation(annotation);
        annotationManager = new AnnotationManager(annotationFilter);
        classAnnotation = annotationFilter;
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
	    if (classAnnotation != null) filteredClasses = classAnnotation.filterClasses(filteredClasses);
	    return filteredClasses;
	}

    public void callMethod(int index, AnnotationFilter annotationFilter, Object[] parameters) {
        annotationManager.invokeMethods(annotationFilter, parameters);
    }

    public void callMethod(int index, AnnotationFilter annotationFilter, Object parameter) {
        callMethod(index, annotationFilter, new Object[]{parameter});
    }

    public void callMethod(int index, AnnotationFilter annotationFilter) {
        callMethod(index, annotationFilter, new Object[]{});
    }

    public void callMethod(AnnotationFilter annotationFilter, Object[] parameters) {
        for (int i = 0; i < getLoadedPlugins().length; i++) {
            callMethod(i, annotationFilter, parameters);
        }
    }

    public void callMethod(AnnotationFilter annotationFilter, Object parameter) {
        callMethod(annotationFilter, new Object[]{parameter});
    }

    public void callMethod(AnnotationFilter annotationFilter) {
        callMethod(annotationFilter, new Object[]{});
    }

    public Object[] getPluginMethodData(int index, String name, AnnotationFilter annotationFilter) {
        return annotationManager.getPluginMethodData(index, name, annotationFilter);
    }
/*
    public Object getPluginData(int index, String name) {
        return annotationManager.getClassData(index, name);
    }//*/
/*
    public Object[] getPluginData(String name) {
        Object[] objects = new Object[getLoadedPlugins().length];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = getPluginData(i, name);
        }
        return objects;
    }
//*/
}
