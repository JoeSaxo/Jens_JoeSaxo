package de.joesaxo.test.annotation;

import de.joesaxo.library.annotation.filter.AnnotationFilter;
import de.joesaxo.library.annotation.Parameter;
import de.joesaxo.library.plugin.PluginManager;
import de.joesaxo.library.plugin.AnnotationPluginManager;

import java.io.File;

/**
 * Created by Jens on 15.04.2017.
 */

public class AnnotationTest {

    public static final String path = "C:\\Users\\Jens\\Documents\\Java\\PluginTest\\";
/*
    public static void main(String[] args) {

        PluginManager pluginManager = createPluginManager();

        pluginManager.loadPluginsFromPath(new File(path));

        AnnotationFilter mStart = createModuleStart();
        AnnotationFilter mResume = createModuleResume();
        AnnotationFilter mStop = createModuleStop();

        pluginManager.callMethod(mStart, "!?!Hello World!?!");
        pluginManager.callMethod(mResume, "!?!Hello World!?!");
        pluginManager.callMethod(mStop, "!?!Hello World!?!");

        pluginManager.callMethod(createModule("PluginTestName")); // no method is affected because [name] is only class name

        System.out.println();

        for (Object object : pluginManager.getPluginData("value")) {
            System.out.println(object);
        }

        System.out.println();

        for (int i = 0; i < pluginManager.getLoadedPlugins().length; i++) {
            for (Object o : pluginManager.getPluginMethodData(i, "value", new Module(APlugin.class))) {
                System.out.println(o);
            }
        }
    }
//*/
    public static AnnotationPluginManager createPluginManager() {
        AnnotationFilter classModule = new AnnotationFilter().setAnnotation(APlugin.class);
        classModule.addParameter(new Parameter("load", true));

        return new AnnotationPluginManager(classModule);
    }

    public static AnnotationFilter createModuleStart() {
        return createModule("startMethod");
    }

    public static AnnotationFilter createModuleResume() {
        return createModule("resumeMethod");
    }

    public static AnnotationFilter createModuleStop() {
        return createModule("stopMethod");
    }

    public static AnnotationFilter createModule(String name) {
        return createModule()
                .addParameter(new Parameter(name));
    }

    public static AnnotationFilter createModule() {
        return new AnnotationFilter()
                .setAnnotation(APlugin.class)
                .addParameter(new Parameter("load", true));
    }
}
