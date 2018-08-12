package gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class GameEvent extends Event {

    public static final EventType<GameEvent> ANY = new EventType<>(Event.ANY, "GAME_EVENT");
    public static final EventType<GameEvent> DROP_BALL = new EventType<>(ANY, "DROP_BALL");
    public static final EventType<GameEvent> NEW_BALL = new EventType<>(ANY, "NEW_BALL");
    public static final EventType<GameEvent> END_GAME = new EventType<>(ANY, "END_GAME");
    public static final EventType<GameEvent> DROP_TARGET_BONUS = new EventType<>(ANY, "DROP_TARGET_BONUS");
    public static final EventType<GameEvent> EXTRA_BALL_BONUS = new EventType<>(ANY, "EXTRA_BALL_BONUS");
    public static final EventType<GameEvent> JACK_POT_BONUS = new EventType<>(ANY, "JACK_POT_BONUS");

    public GameEvent(EventType<? extends Event> eventType){
        super(eventType);
    }
}
