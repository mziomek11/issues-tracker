package com.mateuszziomek.issuestracker.issues.query.application.query.handler;

import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.OrganizationGateway;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.issues.query.application.query.GetDetailsIssueQuery;
import com.mateuszziomek.issuestracker.issues.query.application.query.exception.IssueNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.readmodel.issue.details.DetailsIssueFinder;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.DetailsIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetDetailsIssueQueryHandler implements QueryHandler<GetDetailsIssueQuery, Mono<DetailsIssue>> {
    private final DetailsIssueFinder detailsIssueFinder;
    private final OrganizationGateway organizationGateway;

    /**
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     * @throws OrganizationServiceUnavailableException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     */
    @Override
    public Mono<DetailsIssue> handle(GetDetailsIssueQuery query) {
        return organizationGateway
                .ensureOrganizationHasProjectAndMember(
                        query.getOrganizationId(),
                        query.getProjectId(),
                        query.getMemberId()
                )
                .flatMap(unused -> detailsIssueFinder.findById(query.getIssueId()))
                .switchIfEmpty(Mono.error(new IssueNotFoundException(query.getIssueId())));
    }
}
