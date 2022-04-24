package com.mateuszziomek.issuestracker.organizations.query.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.organizations.query.application.query.GetDetailsOrganizationQuery;
import com.mateuszziomek.issuestracker.organizations.query.application.query.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.query.application.query.handler.GetDetailsOrganizationQueryHandler;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.dispatcher.QueryDispatcher;
import com.mateuszziomek.issuestracker.shared.readmodel.DetailsOrganization;
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
