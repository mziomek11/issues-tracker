package org.example.cqrs.query;

@FunctionalInterface
public interface QueryHandler<T extends Query, U> {
    U handle(T query);
}
