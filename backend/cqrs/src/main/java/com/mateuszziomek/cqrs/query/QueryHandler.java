package com.mateuszziomek.cqrs.query;

@FunctionalInterface
public interface QueryHandler<T, U extends Query<T>> {
    T handle(U query);
}
