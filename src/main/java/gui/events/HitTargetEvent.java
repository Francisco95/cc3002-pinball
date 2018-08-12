package gui.events;

import com.almasb.fxgl.entity.Entity;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;

public class HitTargetEvent extends GameEvent{
    public static final EventType<HitBumperEvent> HIT_TARGET_EVENT =
            new EventType<>(GameEvent.ANY, "HIT_TARGET_EVENT");

    private Entity gameElementTarget;

    public HitTargetEvent(@NamedArg("eventType") EventType<? extends Event> eventType, Entity target) {
        super(eventType);
        this.gameElementTarget = target;
    }

    public Entity getGameElementTarget() {
        return gameElementTarget;
    }
}
