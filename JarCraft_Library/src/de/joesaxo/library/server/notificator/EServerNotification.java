package de.joesaxo.library.server.notificator;

import de.joesaxo.library.annotation.Module;

/**
 * Created by Jens on 16.04.2017.
 */
public enum EServerNotification {

    NEWMESSAGE(), ESTABLISHEDCONNECTION(), TIMEDOUT(), STOPPED();

    Module module;

    EServerNotification() {
        module = new Module(AServer.class).addParameter(this);
    }

    public Module getModule() {
        return module.getCopy();
    }

}
