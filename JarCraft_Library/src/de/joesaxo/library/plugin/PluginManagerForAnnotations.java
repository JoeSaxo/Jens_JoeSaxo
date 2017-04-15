package de.joesaxo.library.plugin;

import java.lang.annotation.Annotation;

/**
 * Created by Jens on 15.04.2017.
 */
public class PluginManagerForAnnotations extends AdvancedPluginManager<Object> {

    public  PluginManagerForAnnotations(AnnotationModule annotationModule) {
        super(annotationModule, Object.class);
    }

    public  PluginManagerForAnnotations(Class<? extends Annotation> annotation) {
        super(annotation, Object.class);
    }

}
