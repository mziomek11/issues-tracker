package org.example.cqrs.query.dispatcher;

import org.example.cqrs.query.Query;
import org.example.cqrs.query.QueryHandler;

public interface QueryDispatcher {
    /**
     * @throws TooManyQueryHandlersException if given query has already registered handler
     */
    <T extends Query> void registerHandler(Class<T> type, QueryHandler<T, ? extends Object> handler);

    /**
     * @throws QueryHandlerNotFoundException if given query has not registered handler
     */
    <T extends Object> T dispatch(Query query);
}
