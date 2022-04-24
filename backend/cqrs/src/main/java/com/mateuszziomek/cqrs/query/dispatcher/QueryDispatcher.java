package com.mateuszziomek.cqrs.query.dispatcher;

import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.cqrs.query.QueryHandler;

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
