package gui.events;

import com.almasb.fxgl.entity.Entity;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * Class that define the event of upgrade a bumper
 *
 * @author Francisco Munoz Ponce
 */
public class UpgradeBumperEvent extends GameEvent {
    private Entity bumper;

    public static final EventType<GameEvent> UPGRADE_BUMPER = new EventType<>(ANY, "UPGRADE_BUMPER");

    public UpgradeBumperEvent(@NamedArg("eventType") EventType<? extends Event> eventType, Entity bumper) {
        super(eventType);
        this.bumper = bumper;
    }

    public Entity getBumper() {
        return bumper;
    }
}
