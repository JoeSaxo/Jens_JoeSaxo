package de.joesaxo.test.annotation;

import de.joesaxo.library.annotation.Module;
import de.joesaxo.library.plugin.PluginManager;
import de.joesaxo.library.plugin.AnnotationPluginManager;

import java.io.File;

/**
 * Created by Jens on 15.04.2017.
 */

public class AnnotationTest {

    public static final String path = "C:\\Users\\Jens\\Documents\\Java\\PluginTest\\";

    public static void main(String[] args) {

        PluginManager pluginManager = createPuginManager();

        pluginManager.loadPluginsFromPath(new File(path));

        Module mStart = createModuleStart();
        Module mResume = createModuleResume();
        Module mStop = createModuleStop();

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

    public static AnnotationPluginManager createPuginManager() {
        Module classModule = new Module(APlugin.class);
        classModule.addParameter("load", true);

        return new AnnotationPluginManager(classModule);
    }

    public static Module createModuleStart() {
        return createModule("startMethod");
    }

    public static Module createModuleResume() {
        return createModule("resumeMethod");
    }

    public static Module createModuleStop() {
        return createModule("stopMethod");
    }

    public static Module createModule(String name) {
        return createModule()
                .addParameter("value", name);
    }

    public static Module createModule() {
        return new Module(APlugin.class)
                .addParameter("load", true);
    }
}
