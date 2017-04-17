package de.joesaxo.library.plugin;

import de.joesaxo.library.annotation.Module;
import java.lang.annotation.Annotation;

/**
 * Created by Jens on 15.04.2017.
 */
public class AnnotationPluginManager extends PluginManager<Object> {

    public AnnotationPluginManager(Module annotationModule) {
        super(annotationModule, Object.class);
    }

    public AnnotationPluginManager(Class<? extends Annotation> annotation) {
        super(annotation, Object.class);
    }


}
