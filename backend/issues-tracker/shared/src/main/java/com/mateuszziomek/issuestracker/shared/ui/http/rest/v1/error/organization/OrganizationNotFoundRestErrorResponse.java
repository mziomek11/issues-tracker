package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public class OrganizationNotFoundRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.ORGANIZATION_NOT_FOUND;
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "Organization with id %s does not exist";

    public static ResponseEntity<RestErrorResponse> asResponseEntity(UUID organizationId) {
        var details = Map.of("organizationId", organizationId);

        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, String.format(MESSAGE, organizationId), details));
    }

    private OrganizationNotFoundRestErrorResponse() {}
}
