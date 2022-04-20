package org.example.issuestracker.users.query.infrastructure.query;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.query.dispatcher.QueryDispatcher;
import org.example.issuestracker.users.query.application.query.GetListUsersQuery;
import org.example.issuestracker.users.query.application.query.handler.GetListUsersQueryHandler;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class UserQueryRegistry {
    private final QueryDispatcher queryDispatcher;
    private final GetListUsersQueryHandler getListUsersQueryHandler;

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(GetListUsersQuery.class, getListUsersQueryHandler);
    }
}
