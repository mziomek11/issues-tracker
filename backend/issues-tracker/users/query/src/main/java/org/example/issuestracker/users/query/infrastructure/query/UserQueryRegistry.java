package org.example.issuestracker.users.query.infrastructure.query;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.query.dispatcher.QueryDispatcher;
import org.example.issuestracker.users.query.application.query.IsUserEmailAvailableQuery;
import org.example.issuestracker.users.query.application.query.handler.IsUserEmailAvailableQueryHandler;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class UserQueryRegistry {
    private final QueryDispatcher queryDispatcher;
    private final IsUserEmailAvailableQueryHandler isUserEmailAvailableQueryHandler;

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(IsUserEmailAvailableQuery.class, isUserEmailAvailableQueryHandler);
    }
}
