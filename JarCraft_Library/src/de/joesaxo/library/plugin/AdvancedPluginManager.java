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

	public void callMethod(AnnotationModule annotationParrameter, Object[] parameters) {
	    for (Object object : getLoadedPlugins()) {
            for (Method method : object.getClass().getDeclaredMethods()) {
                Annotation annotation = getAnnotationFromModule(method, annotationParrameter);
                if (annotation != null) {
                    if (annotationMatchesModule(annotation, annotationParrameter)) {
                        if (isInvokableMethod(method, parameters)) {
                            invokeMethod(method, object, parameters);
                        }
                    }
                }
            }
	    }
    }
}
