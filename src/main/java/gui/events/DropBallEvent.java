package gui.events;

import com.almasb.fxgl.entity.Entity;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * Class that define the drop of a ball
 *
 * @author Francisco Munoz Ponce
 */
public class DropBallEvent extends GameEvent {
    private Entity ball;

    public static final EventType<DropBallEvent> DROP_BALL = new EventType<>(GameEvent.ANY, "DROP_BALL");

    public DropBallEvent(@NamedArg("eventType") EventType<? extends Event> eventType, Entity ball) {
        super(eventType);
        this.ball = ball;
    }

    public Entity getBall() {
        return ball;
    }
}
