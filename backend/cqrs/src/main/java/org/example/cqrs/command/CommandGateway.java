package org.example.cqrs.command;

public interface CommandGateway {
    void dispatch(Object command);
}
