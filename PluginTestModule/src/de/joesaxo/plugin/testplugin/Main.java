package de.joesaxo.plugin.testplugin;

import de.joesaxo.test.APlugin;
import de.joesaxo.test.APluginMethod;

/**
 * Created by Jens on 15.04.2017.
 */
@APlugin(name="MyFirstPluginWithAnnotations", loadable = false)
public class Main {

    @APluginMethod(equals = false)
    public void start(String s) {
        System.out.println("This is a test method for start [" + s + "]");
    }

    @APluginMethod(equals = true)
    public void stop(String s) {
        System.out.println("This is a test method for stop [" + s + "]");
    }

    @APluginMethod(execute=false, equals = false)
    public void resume(String s) {
        System.out.println("This is a test method for resume [" + s + "]");
    }

}
