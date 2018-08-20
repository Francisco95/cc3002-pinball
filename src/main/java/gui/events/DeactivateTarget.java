package gui.events;

import com.almasb.fxgl.entity.Entity;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * class that define an event of deactivate a target
 *
 * @author Francisco Munoz Ponce
 */
public class DeactivateTarget extends GameEvent {
    public static final EventType<GameEvent> DEACTIVATE_TARGET = new EventType<>(ANY, "DEACTIVATE_TARGET");

    private Entity targetGame;
    public DeactivateTarget(@NamedArg("eventType") EventType<? extends Event> eventType, Entity target) {
        super(eventType);
        this.targetGame = target;
    }

    public Entity getTargetGame() {
        return targetGame;
    }
}
