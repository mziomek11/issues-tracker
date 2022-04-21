package org.example.issuestracker.organizations.query.infrastructure.query;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.query.dispatcher.QueryDispatcher;
import org.example.issuestracker.organizations.query.application.query.GetDetailsOrganizationQuery;
import org.example.issuestracker.organizations.query.application.query.handler.GetDetailsOrganizationQueryHandler;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class OrganizationQueryRegistry {
    private final QueryDispatcher queryDispatcher;
    private final GetDetailsOrganizationQueryHandler getDetailsOrganizationQueryHandler;

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(GetDetailsOrganizationQuery.class, getDetailsOrganizationQueryHandler);
    }
}
