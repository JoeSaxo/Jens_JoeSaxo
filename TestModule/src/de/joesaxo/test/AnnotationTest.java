package de.joesaxo.test;

import de.joesaxo.library.plugin.AnnotationModule;
import de.joesaxo.library.plugin.AdvancedPluginManager;
import de.joesaxo.library.plugin.PluginManagerForAnnotations;
import de.joesaxo.library.plugin.annotations.APlugin;
import de.joesaxo.library.plugin.annotations.APluginMethod;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jens on 15.04.2017.
 */

public class AnnotationTest {


    public static void main(String[] args) {
        AdvancedPluginManager advancedPluginManager = createPuginManager();
        File directory = new File("C:\\Users\\Jens\\Documents\\Java\\PluginTest\\");
        try {
            advancedPluginManager.loadPluginsFromPath(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnnotationModule module = creaMethodModule();
        advancedPluginManager.callMethod(module, "!?!Hello World!?!");
        System.out.println();

        for (Object object : advancedPluginManager.getPluginData("name")) {
            System.out.println(object);
        }

    }

    public static PluginManagerForAnnotations createPuginManager() {
        AnnotationModule classModule = new AnnotationModule(APlugin.class);
        classModule.addParameter("load", true);

        return new PluginManagerForAnnotations(classModule);
    }

    public static AnnotationModule creaMethodModule() {
        return new AnnotationModule(APluginMethod.class)
            .addParameter("execute", true)
            .addParameter("name", "");

    }
}
