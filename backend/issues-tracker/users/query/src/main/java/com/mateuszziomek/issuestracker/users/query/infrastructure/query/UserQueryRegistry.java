package com.mateuszziomek.issuestracker.users.query.infrastructure.query;

import com.mateuszziomek.issuestracker.users.query.application.query.GetListUsersQuery;
import com.mateuszziomek.issuestracker.users.query.application.query.handler.GetListUsersQueryHandler;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.dispatcher.QueryDispatcher;
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
