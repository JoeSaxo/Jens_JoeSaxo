package de.joesaxo.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jens on 15.04.2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface APlugin {

    boolean load() default true;
    // pre defined Value

    String value();
    /*
     * Method "value" can be written @APlugin("")
     * and doesn't need (value="123")
     * only if another Method return is defined
     * like "load" it needs to be written
     * @APlugin(value="", load=false) or
     * @APlugin(load=false, value="")
     *
     */


}
