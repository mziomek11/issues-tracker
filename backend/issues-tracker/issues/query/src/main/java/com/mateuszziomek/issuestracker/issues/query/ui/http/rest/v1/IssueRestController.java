package com.mateuszziomek.issuestracker.issues.query.ui.http.rest.v1;

import com.mateuszziomek.cqrs.query.dispatcher.QueryDispatcher;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.issues.query.application.query.GetDetailsIssueQuery;
import com.mateuszziomek.issuestracker.issues.query.application.query.GetListIssuesQuery;
import com.mateuszziomek.issuestracker.issues.query.application.query.exception.IssueNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.query.handler.GetDetailsIssueQueryHandler;
import com.mateuszziomek.issuestracker.issues.query.application.query.handler.GetListIssuesQueryHandler;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.DetailsIssue;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.ListIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/issue-management")
@RequiredArgsConstructor
public class IssueRestController {
    private final QueryDispatcher queryDispatcher;

    /**
     * @throws OrganizationMemberNotFoundException see {@link GetListIssuesQueryHandler#handle(GetListIssuesQuery)}
     * @throws OrganizationNotFoundException see {@link GetListIssuesQueryHandler#handle(GetListIssuesQuery)}
     * @throws OrganizationProjectNotFoundException {@link GetListIssuesQueryHandler#handle(GetListIssuesQuery)}
     * @throws OrganizationServiceUnavailableException {@link GetListIssuesQueryHandler#handle(GetListIssuesQuery)}
     */
    @GetMapping("/organizations/{organizationId}/projects/{projectId}/issues")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ListIssue> getListIssues(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ID) UUID userId,
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId
    ) {
        var getListIssuesQuery = GetListIssuesQuery
                .builder()
                .memberId(userId)
                .organizationId(organizationId)
                .projectId(projectId)
                .build();

        return queryDispatcher.dispatch(getListIssuesQuery);
    }

    /**
     * @throws IssueNotFoundException see {@link GetDetailsIssueQueryHandler#handle(GetDetailsIssueQuery)}
     * @throws OrganizationMemberNotFoundException see {@link GetDetailsIssueQueryHandler#handle(GetDetailsIssueQuery)}
     * @throws OrganizationNotFoundException see {@link GetDetailsIssueQueryHandler#handle(GetDetailsIssueQuery)}
     * @throws OrganizationProjectNotFoundException {@link GetDetailsIssueQueryHandler#handle(GetDetailsIssueQuery)}
     * @throws OrganizationServiceUnavailableException {@link GetDetailsIssueQueryHandler#handle(GetDetailsIssueQuery)}
     */
    @GetMapping("/organizations/{organizationId}/projects/{projectId}/issues/{issueId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<DetailsIssue> getDetailsIssue(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ID) UUID userId,
            @PathVariable UUID organizationId,
            @PathVariable UUID projectId,
            @PathVariable UUID issueId
    ) {
        var getDetailsIssueQuery = GetDetailsIssueQuery
                .builder()
                .memberId(userId)
                .organizationId(organizationId)
                .projectId(projectId)
                .issueId(issueId)
                .build();

        return queryDispatcher.dispatch(getDetailsIssueQuery);
    }
}
