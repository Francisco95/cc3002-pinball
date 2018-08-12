package gui.events;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;
import logic.bonus.Bonus;

public class BonusTriggerEvent extends GameEvent {

    public static final EventType<HitBumperEvent> BONUS_TRIGGER_EVENT =
            new EventType<>(GameEvent.ANY, "BONUS_TRIGGER_EVENT");

    private Bonus bonus;
    public BonusTriggerEvent(@NamedArg("eventType") EventType<? extends Event> eventType, Bonus bonus) {
        super(eventType);
        this.bonus = bonus;
    }

    public Bonus getBonus() {
        return bonus;
    }
}
