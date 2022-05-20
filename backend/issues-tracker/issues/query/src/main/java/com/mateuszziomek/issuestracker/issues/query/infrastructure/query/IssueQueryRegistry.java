package com.mateuszziomek.issuestracker.issues.query.infrastructure.query;

import com.mateuszziomek.cqrs.query.dispatcher.QueryDispatcher;
import com.mateuszziomek.issuestracker.issues.query.application.query.GetDetailsIssueQuery;
import com.mateuszziomek.issuestracker.issues.query.application.query.GetListIssuesQuery;
import com.mateuszziomek.issuestracker.issues.query.application.query.handler.GetDetailsIssueQueryHandler;
import com.mateuszziomek.issuestracker.issues.query.application.query.handler.GetListIssuesQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class IssueQueryRegistry {
    private final QueryDispatcher queryDispatcher;
    private final GetListIssuesQueryHandler getListIssuesQueryHandler;
    private final GetDetailsIssueQueryHandler getDetailsIssueQueryHandler;

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(GetListIssuesQuery.class, getListIssuesQueryHandler);
        queryDispatcher.registerHandler(GetDetailsIssueQuery.class, getDetailsIssueQueryHandler);
    }
}
