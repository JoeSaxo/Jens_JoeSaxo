package de.joesaxo.library.server.notificator;

import de.joesaxo.library.annotation.*;
import de.joesaxo.library.annotation.filter.AnnotationFilter;

/**
 * Created by Jens on 16.04.2017.
 */
public enum EServerNotification {

    ESTABLISHEDCONNECTION(), NEWMESSAGE(), TIMEDOUT(), STOPPED();

    AnnotationFilter filter;

    EServerNotification() {
        filter = new AnnotationFilter();
        filter.setAnnotation(AServer.class);
        filter.addParameter(new Parameter("value", this));
    }

    public void callAnnotation(AnnotationManager annotationManager, String clientId, Object attribute) {
        Parameter clientSet = new Parameter("client", clientId);
        Parameter clientUnSet = new Parameter("client", "");
        Parameter messageSet = new Parameter("message", attribute);
        Parameter messageUnSet = new Parameter("message", "");
        switch (this) {
            case ESTABLISHEDCONNECTION:
                annotationManager.invokeMethods(createMethodFilter(clientUnSet), clientId);
                annotationManager.invokeMethods(createMethodFilter(clientSet));
                break;
            case NEWMESSAGE:
                annotationManager.invokeMethods(createMethodFilter(clientUnSet, messageUnSet), new Object[]{clientId, attribute});
                annotationManager.invokeMethods(createMethodFilter(clientSet, messageUnSet), attribute);
                annotationManager.invokeMethods(createMethodFilter(clientUnSet, messageSet), clientId);
                annotationManager.invokeMethods(createMethodFilter(clientSet, messageSet));
                break;
            case TIMEDOUT:
                annotationManager.invokeMethods(createMethodFilter(clientUnSet), new Object[]{clientId, attribute});
                annotationManager.invokeMethods(createMethodFilter(clientSet), attribute);
                break;
            case STOPPED:
                annotationManager.invokeMethods(createMethodFilter(clientUnSet), clientId);
                annotationManager.invokeMethods(createMethodFilter(clientSet));
                break;
            default:
                break;
        }
    }

    private AnnotationFilter createMethodFilter(Parameter parameter) {
        return createMethodFilter(new Parameter[]{parameter});
    }

    private AnnotationFilter createMethodFilter(Parameter firstParameter, Parameter secondParameter) {
        return createMethodFilter(new Parameter[]{firstParameter, secondParameter});
    }

    private AnnotationFilter createMethodFilter(Parameter[] parameters) {
        AnnotationFilter filter = new AnnotationFilter();
        filter.setAnnotation(AServer.class);
        filter.setParameter(new Parameter("value", this));
        filter.addParameters(parameters);
        return filter;
    }

}
