package de.joesaxo.game.twodimesional.background.plugable;

import de.joesaxo.game.twodimesional.background.plugable.eventhandler.Event;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventHandler;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType;
import de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.GameStateEvent;
import de.joesaxo.game.twodimesional.background.plugable.window.GameFrame;
import de.joesaxo.game.twodimesional.background.plugable.world.WorldLoader;
import org.jarcraft.library.time.StopWatch;
import org.jarcraft.library.time.Time;

import static de.joesaxo.game.twodimesional.background.plugable.GameComponents.*;
import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.STOP_EVENT;
import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.EventType.TICK_OVERTIME_EVENT;
import static de.joesaxo.game.twodimesional.background.plugable.eventhandler.event.GameStateEvent.GameState.TICK_OVERTIME;

/**
 * Created by Jens on 05.05.2017.
 */
public class Main {

    public static final int ticksPerSecond = 50;

    private boolean running;



    public static void main(String[] args) {
        new Main();
    }

    public Main() {

        start();

        run();

        stop();

    }

    public void start() {
        gameConfig = new GameConfig();
        gameConfig.load();
        EventHandler.add(this);
        eventListener = new EventListener();
        EventHandler.add(eventListener);
        gameFrame = new GameFrame();
        worldLoader = new WorldLoader(path);
        worldLoader.loadWorlds();
        EventHandler.invoke(GameStateEvent.STARTING);

        running = true;
    }

    private void run() {
        long timePerTick = 1000 / ticksPerSecond;
        StopWatch stopWatch = new StopWatch();
        while (running) {
            stopWatch.start();


            tick();

            stopWatch.stop();
            long deltaTime = stopWatch.getTimeDelta(timePerTick);
            if (deltaTime > 0) {
                Time.WaitMillis(deltaTime);
            } else {
                EventHandler.invoke(new GameStateEvent(TICK_OVERTIME, -deltaTime));
            }
            stopWatch.reset();
        }
    }

    @Event(TICK_OVERTIME_EVENT)
    public void overload(GameStateEvent e) {
        System.out.println("Can't keep up! Did the system time changed or is the system overloaded? " + e.getTime());
    }

    public void tick() {
        EventHandler.invoke(GameStateEvent.TICK);
    }

    @Event(STOP_EVENT)
    public void stop() {
        running = false;
    }


}
