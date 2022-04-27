package com.mateuszziomek.issuestracker.users.query.infrastructure.query;

import com.mateuszziomek.issuestracker.users.query.application.query.GetJWTQuery;
import com.mateuszziomek.issuestracker.users.query.application.query.GetListUsersQuery;
import com.mateuszziomek.issuestracker.users.query.application.query.handler.GetJWTQueryHandler;
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
    private final GetJWTQueryHandler getJWTQueryHandler;

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(GetListUsersQuery.class, getListUsersQueryHandler);
        queryDispatcher.registerHandler(GetJWTQuery.class, getJWTQueryHandler);
    }
}
