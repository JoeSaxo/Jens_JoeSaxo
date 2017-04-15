package de.joesaxo.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jens on 15.04.2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface APluginMethod {

    boolean execute() default true;
    boolean equals();

}
