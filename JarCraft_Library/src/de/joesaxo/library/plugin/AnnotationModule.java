package de.joesaxo.library.plugin;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jens on 15.04.2017.
 */
public class AnnotationModule {

    private List<String> parameterName;
    private List<Object> parameterValue;

    private Class<? extends Annotation> annotation;

    public static boolean isAllowedAnnotation(Class<? extends Annotation> annotation) {
        return annotation.getAnnotation(Retention.class).value().equals(RetentionPolicy.RUNTIME);
    }

    public AnnotationModule(Class<? extends Annotation> annotation) {
        if (!isAllowedAnnotation(annotation)) {
            throw new IllegalArgumentException("Annotation " + annotation + "must be accessible at runntime!");
        }
        this.annotation = annotation;
        parameterName = new ArrayList<>();
        parameterValue = new ArrayList<>();
    }

    public AnnotationModule addParameter(String parameter, Object value) {
        parameterName.add(parameter);
        parameterValue.add(value);
        return this;
    }

    public int parameters() {
        return parameterName.size();
    }

    public String getParameterName(int index) {
        return parameterName.get(index);
    }

    public Object getParameterValue(int index) {
        return parameterValue.get(index);
    }

    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

}
