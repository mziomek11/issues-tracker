package com.mateuszziomek.cqrs.query.dispatcher;

import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.cqrs.query.QueryHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDispatcher {
    private final Map<Class<? extends Query>, List<QueryHandler<? extends Object, Query<? extends Object>>>> registry = new HashMap<>();

    /**
     * @throws TooManyQueryHandlersException if given query has already registered handler
     */
    public <T> void registerHandler(Class<? extends Query<T>> type, QueryHandler<T, ? extends Object> handler) {
        var handlers = registry.computeIfAbsent(type, c -> new ArrayList<>());

        // Class<Query<String>> =

        if (!handlers.isEmpty()) {
            throw new TooManyQueryHandlersException();
        }

        handlers.add((QueryHandler) handler);
    }

    /**
     * @throws QueryHandlerNotFoundException if given query has not registered handler
     */
    public <T> T dispatch(Query<T> query) {
        var handlers = registry.get(query.getClass());

        if (handlers == null) {
            throw new QueryHandlerNotFoundException();
        }

        return (T) handlers.get(0).handle(query);
    }
}
