package org.example.issuestracker.organizations.query.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.query.dispatcher.QueryDispatcher;
import org.example.issuestracker.organizations.query.application.query.GetDetailsOrganizationQuery;
import org.example.issuestracker.organizations.query.application.query.exception.OrganizationNotFoundException;
import org.example.issuestracker.organizations.query.application.query.handler.GetDetailsOrganizationQueryHandler;
import org.example.issuestracker.shared.readmodel.DetailsOrganization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organization-management")
@RequiredArgsConstructor
public class OrganizationRestController {
    private final QueryDispatcher queryDispatcher;

    /**
     * @throws OrganizationNotFoundException see {@link GetDetailsOrganizationQueryHandler#handle(GetDetailsOrganizationQuery)}
     */
    @GetMapping("/organizations/{organizationId}")
    public ResponseEntity<DetailsOrganization> getDetailsOrganization(@PathVariable UUID organizationId) {
        var getDetailsOrganizationQuery = new GetDetailsOrganizationQuery(organizationId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(queryDispatcher.dispatch(getDetailsOrganizationQuery));
    }
}
