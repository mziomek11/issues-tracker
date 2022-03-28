package org.example.cqrs.command;

public class TooManyCommandHandlersException extends IllegalStateException {
    public TooManyCommandHandlersException() {
        super("Command can only have one handler.");
    }
}
