package com.mateuszziomek.issuestracker.issues.query.application.query.handler;

import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.issues.query.application.query.GetDetailsIssueQuery;
import com.mateuszziomek.issuestracker.issues.query.application.query.exception.IssueNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.service.organization.OrganizationService;
import com.mateuszziomek.issuestracker.issues.query.application.service.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.service.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.service.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.readmodel.issue.details.DetailsIssueFinder;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.DetailsIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetDetailsIssueQueryHandler implements QueryHandler<Mono<DetailsIssue>, GetDetailsIssueQuery> {
    private final DetailsIssueFinder detailsIssueFinder;
    private final OrganizationService organizationService;

    /**
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws OrganizationMemberNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     * @throws OrganizationNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     */
    @Override
    public Mono<DetailsIssue> handle(GetDetailsIssueQuery query) {
        return organizationService
                .ensureOrganizationHasProjectAndMember(
                        query.getOrganizationId(),
                        query.getProjectId(),
                        query.getMemberId()
                )
                .flatMap(unused -> detailsIssueFinder.findById(query.getIssueId()))
                .switchIfEmpty(Mono.error(new IssueNotFoundException(query.getIssueId())));
    }
}
