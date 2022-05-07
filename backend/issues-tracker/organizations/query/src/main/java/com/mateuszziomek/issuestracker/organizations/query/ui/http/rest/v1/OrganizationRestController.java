package com.mateuszziomek.issuestracker.organizations.query.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.organizations.query.application.query.GetDetailsOrganizationQuery;
import com.mateuszziomek.issuestracker.organizations.query.application.query.GetListOrganizationsQuery;
import com.mateuszziomek.issuestracker.organizations.query.application.query.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.query.application.query.handler.GetDetailsOrganizationQueryHandler;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.exception.AccessDeniedException;
import com.mateuszziomek.issuestracker.shared.readmodel.ListOrganization;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.dispatcher.QueryDispatcher;
import com.mateuszziomek.issuestracker.shared.readmodel.DetailsOrganization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

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
     * @throws AccessDeniedException if user is not {@link UserRole#SYSTEM}
     * @throws OrganizationNotFoundException see {@link GetDetailsOrganizationQueryHandler#handle(GetDetailsOrganizationQuery)}
     */
    @GetMapping("/organizations/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DetailsOrganization> getDetailsOrganization(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ROLE) UserRole userRole,
            @PathVariable UUID organizationId
    ) {
        if (!UserRole.SYSTEM.equals(userRole)) {
            throw new AccessDeniedException();
        }

        return queryDispatcher.dispatch(new GetDetailsOrganizationQuery(organizationId));
    }
}
