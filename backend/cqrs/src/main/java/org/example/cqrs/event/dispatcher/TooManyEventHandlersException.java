package org.example.cqrs.event.dispatcher;

public class TooManyEventHandlersException extends IllegalStateException {
    public TooManyEventHandlersException() {
        super("Event can only have one handler.");
    }
}
