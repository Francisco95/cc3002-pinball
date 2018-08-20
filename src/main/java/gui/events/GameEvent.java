package gui.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Class that define some events of the game like Bonus events (JackPot, DropTarget and ExtraBall)
 *
 * @author Francisco Munoz Ponce
 */
public class GameEvent extends Event {

    public static final EventType<GameEvent> ANY = new EventType<>(Event.ANY, "GAME_EVENT");
    public static final EventType<GameEvent> DROP_TARGET_BONUS = new EventType<>(ANY, "DROP_TARGET_BONUS");
    public static final EventType<GameEvent> EXTRA_BALL_BONUS = new EventType<>(ANY, "EXTRA_BALL_BONUS");
    public static final EventType<GameEvent> JACK_POT_BONUS = new EventType<>(ANY, "JACK_POT_BONUS");

    public GameEvent(EventType<? extends Event> eventType){
        super(eventType);
    }
}
