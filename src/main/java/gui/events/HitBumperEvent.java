package gui.events;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;
import logic.gameelements.bumper.Bumper;

public class HitBumperEvent extends GameEvent {
    private static final EventType<HitBumperEvent> HIT_BUMPER_EVENT =
            new EventType<>(GameEvent.ANY, "HIT_BUMPER_EVENT");

    private Bumper GameElementBumper;

    public HitBumperEvent(@NamedArg("eventType") EventType<? extends Event> eventType, Bumper bumper) {
        super(eventType);
        this.GameElementBumper = bumper;
    }

    public Bumper getGameElementBumper() {
        return GameElementBumper;
    }
}
