package de.joesaxo.game.twodimesional.background.plugable.eventhandler;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.IEvent;
import de.joesaxo.library.annotation.AnnotationManager;
import de.joesaxo.library.annotation.Parameter;
import de.joesaxo.library.annotation.filter.AnnotationFilter;
import de.joesaxo.library.array.Array;

import java.lang.annotation.Annotation;

/**
 * Created by Jens on 05.05.2017.
 */
public class EventHandler {


    static AnnotationManager annotationManager = new AnnotationManager();

    public static void add(Object cls) {
        annotationManager.setClasses(Array.addToArray(annotationManager.getClasses(), new Object[]{cls}));
    }

    public static AnnotationFilter createFilter(Class<? extends Annotation> annotation, Parameter[] parameters) {
        return new AnnotationFilter(annotation, parameters);
    }

    public static AnnotationFilter createFilter(Class<? extends Annotation> annotation, Parameter parameter) {
        return new AnnotationFilter(annotation, parameter);
    }

    public static AnnotationFilter createFilter(Class<? extends Annotation> annotation) {
        return new AnnotationFilter(annotation);
    }

    public static Object[] invoke(IEvent event) {

        Object[] firstReturnStatements = EventHandler.annotationManager.invokeMethods(new AnnotationFilter(Event.class, new Parameter("value", event.getEventType())), event);
        Object[] secondReturnStatements = EventHandler.annotationManager.invokeMethods(new AnnotationFilter(Event.class, new Parameter("value", event.getEventType())));

        return Array.addToArray(firstReturnStatements, secondReturnStatements);
    }
}
