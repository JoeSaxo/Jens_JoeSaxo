package de.joesaxo.plugin.testplugin;

import de.joesaxo.library.plugin.annotations.APlugin;
import de.joesaxo.library.plugin.annotations.APluginMethod;

/**
 * Created by Jens on 15.04.2017.
 */
@APlugin(name="MyFirstPluginWithAnnotations")
public class Main {

    @APluginMethod()
    public void start(String s) {
        System.out.println("This is a test method for start [" + s + "]");
    }

    @APluginMethod()
    public void stop(String s) {
        System.out.println("This is a test method for stop [" + s + "]");
    }

    @APluginMethod(execute=false)
    public void resume(String s) {
        System.out.println("This is a test method for resume [" + s + "]");
    }

}
