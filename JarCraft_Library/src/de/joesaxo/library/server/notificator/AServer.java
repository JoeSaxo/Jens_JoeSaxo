package de.joesaxo.library.server.notificator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jens on 16.04.2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AServer {

    EServerNotification value();

    String type() default "";

}
