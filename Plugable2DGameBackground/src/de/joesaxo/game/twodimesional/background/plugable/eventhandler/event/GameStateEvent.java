package de.joesaxo.game.twodimesional.background.plugable.eventhandler.event;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;

import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.*;

/**
 * Created by Jens on 06.05.2017.
 */
public class GameStateEvent implements IEvent {

    public static GameStateEvent STARTING = new GameStateEvent(GameState.STARTING);
    public static GameStateEvent TICK = new GameStateEvent(GameState.TICK);
    public static GameStateEvent STOPPING = new GameStateEvent(GameState.STOPPING);

    private GameState gameState;

    private long time;

    public GameStateEvent(GameState gameState) {
        this(gameState, -1);
    }


    public GameStateEvent(GameState gameState, long time) {
        this.gameState = gameState;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    @Override
    public EventType getEventType() {
        return gameState.getEventType();
    }

    public enum GameState {

        STARTING(START_EVENT),

        TICK(TICK_EVENT),

        TICK_OVERTIME(TICK_OVERTIME_EVENT),

        STOPPING(STOP_EVENT);

        EventType eventType;

        GameState(EventType eventType) {
            this.eventType = eventType;
        }

        public EventType getEventType() {
            return eventType;
        }
    }

}
