package de.joesaxo.game.twodimesional.background.plugable.eventhandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jens on 05.05.2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Event {

    EventType value();

}
