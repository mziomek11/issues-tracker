package com.mateuszziomek.issuestracker.issues.query.ui.http.rest.v1;

import com.mateuszziomek.cqrs.query.dispatcher.QueryDispatcher;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.OrganizationGateway;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.issues.query.application.query.GetListIssuesQuery;
import com.mateuszziomek.issuestracker.issues.query.application.query.handler.GetListIssuesQueryHandler;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.ListIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
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
}
