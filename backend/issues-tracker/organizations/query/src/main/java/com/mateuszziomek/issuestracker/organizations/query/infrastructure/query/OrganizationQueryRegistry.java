package com.mateuszziomek.issuestracker.organizations.query.infrastructure.query;

import com.mateuszziomek.issuestracker.organizations.query.application.query.GetListOrganizationsQuery;
import com.mateuszziomek.issuestracker.organizations.query.application.query.handler.GetListOrganizationsQueryHandler;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.dispatcher.QueryDispatcher;
import com.mateuszziomek.issuestracker.organizations.query.application.query.GetDetailsOrganizationQuery;
import com.mateuszziomek.issuestracker.organizations.query.application.query.handler.GetDetailsOrganizationQueryHandler;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class OrganizationQueryRegistry {
    private final QueryDispatcher queryDispatcher;
    private final GetDetailsOrganizationQueryHandler getDetailsOrganizationQueryHandler;
    private final GetListOrganizationsQueryHandler getListOrganizationsQueryHandler;

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(GetDetailsOrganizationQuery.class, getDetailsOrganizationQueryHandler);
        queryDispatcher.registerHandler(GetListOrganizationsQuery.class, getListOrganizationsQueryHandler);
    }
}
