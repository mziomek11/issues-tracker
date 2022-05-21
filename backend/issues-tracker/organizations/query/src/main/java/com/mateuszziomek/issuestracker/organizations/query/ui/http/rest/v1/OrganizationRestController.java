package com.mateuszziomek.issuestracker.organizations.query.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.organizations.query.application.query.GetDetailsOrganizationQuery;
import com.mateuszziomek.issuestracker.organizations.query.application.query.GetListInvitationsQuery;
import com.mateuszziomek.issuestracker.organizations.query.application.query.GetListOrganizationsQuery;
import com.mateuszziomek.issuestracker.organizations.query.application.query.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.query.application.query.handler.GetDetailsOrganizationQueryHandler;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.exception.AccessDeniedException;
import com.mateuszziomek.issuestracker.shared.readmodel.invitation.ListInvitation;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.ListOrganization;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.dispatcher.QueryDispatcher;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organization-management")
@RequiredArgsConstructor
public class OrganizationRestController {
    private final QueryDispatcher queryDispatcher;

    @GetMapping("/organizations")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ListOrganization> getListOrganizations(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ID) UUID userId
    ) {
        return queryDispatcher.dispatch(new GetListOrganizationsQuery(userId));
    }

     /**
     * @throws AccessDeniedException see {@link GetDetailsOrganizationQueryHandler#handle(GetDetailsOrganizationQuery)}
     * @throws OrganizationNotFoundException see {@link GetDetailsOrganizationQueryHandler#handle(GetDetailsOrganizationQuery)}
     */
    @GetMapping("/organizations/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<DetailsOrganization> getDetailsOrganization(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ROLE) UserRole userRole,
            @PathVariable UUID organizationId
    ) {
        return queryDispatcher.dispatch(new GetDetailsOrganizationQuery(organizationId, userRole));
    }

    @GetMapping("/invitations")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ListInvitation> getListInvitations(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ID) UUID userId
    ) {
        var getListInvitationQuery = GetListInvitationsQuery
                .builder()
                .memberId(userId)
                .build();

        return queryDispatcher.dispatch(getListInvitationQuery);
    }
}
