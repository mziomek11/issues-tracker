package com.mateuszziomek.issuestracker.issues.query.application.query.handler;

import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.OrganizationGateway;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.issues.query.application.query.GetListIssuesQuery;
import com.mateuszziomek.issuestracker.issues.query.readmodel.issue.list.ListIssueFilter;
import com.mateuszziomek.issuestracker.issues.query.readmodel.issue.list.ListIssueFinder;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.ListIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetListIssuesQueryHandler implements QueryHandler<GetListIssuesQuery, Flux<ListIssue>> {
    private final ListIssueFinder listIssueFinder;
    private final OrganizationGateway organizationGateway;

    /**
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     * @throws OrganizationServiceUnavailableException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     */
    @Override
    public Flux<ListIssue> handle(GetListIssuesQuery query) {
        return organizationGateway
                .ensureOrganizationHasProjectAndMember(
                    query.getOrganizationId(),
                    query.getProjectId(),
                    query.getMemberId()
                )
                .flux()
                .flatMap((unused) -> listIssueFinder.findByFilter(createFilter(query)));
    }

    private ListIssueFilter createFilter(GetListIssuesQuery query) {
        return ListIssueFilter
                .builder()
                .projectId(query.getProjectId())
                .build();
    }
}
