package de.joesaxo.test;

import de.joesaxo.library.plugin.AnnotationModule;
import de.joesaxo.library.plugin.AdvancedPluginManager;
import de.joesaxo.library.plugin.PluginManagerForAnnotations;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jens on 15.04.2017.
 */

public class AnnotationTest {


    public static void main(String[] args) {

        //Class<APluginMethod> annotation = APluginMethod.class;

        AnnotationModule methodModule = new AnnotationModule(APluginMethod.class);
        methodModule.addParameter("execute", true);
        //methodModule.addParameter("equals", false);

        AnnotationModule classModule = new AnnotationModule(APlugin.class);
        classModule.addParameter("loadable", true);

        Object[] parameters;
        parameters = new Object[]{"!?!Hello World!?!"};

        AdvancedPluginManager advancedPluginManager = new PluginManagerForAnnotations(classModule);
        File directory = new File("C:\\Users\\Jens\\Documents\\Java\\PluginTest\\");
        try {
            advancedPluginManager.loadPluginsFromPath(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(pam.getLoadedPlugins().size());
        advancedPluginManager.callMethod(methodModule, parameters);
    }
/*
    private static <A  extends Annotation> boolean isAnnotated(Method method, Class<A> annotation, Parameter[] annotationParameters) {
        for (Annotation methodAnnotation : method.getDeclaredAnnotations()) {
            if (methodAnnotation.annotationType().equals(annotation)) {
                return matchesParameters(methodAnnotation, annotationParameters);
            }
        }
        return false;
    }

    private static boolean matchesParameters(Annotation aObject, Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            if (!parameterIsEquals(parameter, aObject)) return false;
        }
        return true;
    }

    private static boolean parameterIsEquals(Parameter parameter, Annotation annotation) {
        for (Method method : annotation.annotationType().getDeclaredMethods()) {
            if (!isObjetctMethod(method)) {
                if (method.getName().equals(parameter.getName())) {
                    if (callMethod(method, annotation, new Object[]{}).equals(parameter.getValue())) return true;
                }
            }
        }
        return false;
    }//*/


}
