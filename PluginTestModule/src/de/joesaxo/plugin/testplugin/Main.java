package de.joesaxo.plugin.testplugin;

import de.joesaxo.test.annotation.APlugin;

/**
 * Created by Jens on 15.04.2017.
 */
@APlugin("PluginTestName")
public class Main {

    @APlugin("startMethod") // will be launched because [load] is pre defined as [true] is (APlugin)
    public void start(String s) {
        System.out.println("This is a test method for start [" + s + "]");
    }

    @APlugin(load=false, value="resumeMethod") // will not be lauched because [load] is not [true]
    public void resume(String s) {
        System.out.println("This is a test method for resume [" + s + "]");
    }

    @APlugin("stopMethod")
    public void stop(String s) {
        System.out.println("This is a test method for stop [" + s + "]");
    }

    @APlugin("stopMethod") // will not be launched because Parameter [java.lag.String] is Missing.
    public void stopNow() {
        stop("Now");
    }

    /*
     * This method is not recognised because an annotation is missing.
     * That's why it's never launched
      */
    public void never() {
        resume("NEVER__LAUNCHED");
    }

}
