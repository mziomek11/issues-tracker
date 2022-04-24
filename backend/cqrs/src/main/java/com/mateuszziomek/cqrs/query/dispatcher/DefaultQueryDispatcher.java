package com.mateuszziomek.cqrs.query.dispatcher;

import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.cqrs.query.QueryHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultQueryDispatcher implements QueryDispatcher {
    private final Map<Class<? extends Query>, List<QueryHandler<Query, Object>>> registry = new HashMap<>();

    /**
     * @throws TooManyQueryHandlersException see {@link QueryDispatcher#registerHandler(Class, QueryHandler)}
     */
    @Override
    public <T extends Query> void registerHandler(Class<T> type, QueryHandler<T, ? extends Object> handler) {
        var handlers = registry.computeIfAbsent(type, c -> new ArrayList<>());

        if (!handlers.isEmpty()) {
            throw new TooManyQueryHandlersException();
        }

        handlers.add((QueryHandler<Query, Object>) handler);
    }

    /**
     * @throws QueryHandlerNotFoundException see {@link QueryDispatcher#dispatch(Query)}
     */
    @Override
    public Object dispatch(Query query) {
        var handlers = registry.get(query.getClass());

        if (handlers == null) {
            throw new QueryHandlerNotFoundException();
        }

        return handlers.get(0).handle(query);
    }
}
