package com.mateuszziomek.cqrs.domain;

import com.mateuszziomek.cqrs.event.BaseEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {
    public static final int INITIAL_VERSION = -1;

    private int version = INITIAL_VERSION;
    private final List<BaseEvent> changes = new ArrayList<>();
    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public abstract AggregateId getId();

    public List<BaseEvent> getUncommittedChanges() {
        return changes;
    }

    public void markChangesAsCommitted() {
        changes.clear();
    }

    public void raiseEvent(BaseEvent event) {
        applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events) {
        events.forEach(event -> applyChange(event, false));
    }

    private void applyChange(BaseEvent event, boolean isNew) {
        try {
            var method = getClass().getDeclaredMethod("on", event.getClass());
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            logger.log(
                    Level.WARNING,
                    MessageFormat.format(
                            "The 'on' method was not found in the aggregate for {0}",
                            event.getClass()
                    )
            );
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error invoking event to aggregate", e);
        } finally {
            if (isNew) {
                changes.add(event);
            }
        }
    }
}
