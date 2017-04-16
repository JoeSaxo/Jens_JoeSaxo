package de.joesaxo.test;

import de.joesaxo.library.plugin.AnnotationModule;
import de.joesaxo.library.plugin.PluginManager;
import de.joesaxo.library.plugin.AnnotationPluginManager;
import de.joesaxo.library.plugin.annotations.APlugin;
import de.joesaxo.library.plugin.annotations.APluginMethod;

import java.io.File;

/**
 * Created by Jens on 15.04.2017.
 */

public class AnnotationTest {


    public static void main(String[] args) {


        PluginManager pluginManager = createPuginManager();
        File directory = new File("C:\\Users\\Jens\\Documents\\Java\\PluginTest\\");
        pluginManager.loadPluginsFromPath(directory);

        pluginManager.callMethod(createMethodModule1(), "!?!Hello World!?!");

        System.out.println();

        for (Object object : pluginManager.getPluginData("value")) {
            System.out.println(object.toString());
        }

    }

    public static AnnotationPluginManager createPuginManager() {
        AnnotationModule classModule = new AnnotationModule(APlugin.class);
        classModule.addParameter("load", true);

        return new AnnotationPluginManager(classModule);
    }

    public static AnnotationModule createMethodModule1() {
        return new AnnotationModule(APluginMethod.class)
                .addParameter("execute", true)
                .addParameter("value", "");

    }
}
