package org.example.cqrs.infrastructure;

public interface CommandGateway {
    void dispatch(Object command);
}
