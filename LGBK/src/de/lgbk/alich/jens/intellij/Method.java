package de.lgbk.alich.jens.intellij;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jens on 22.04.2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Method {

    //value == description (value is the default method name)
    String value();

    boolean execute() default true;

}
