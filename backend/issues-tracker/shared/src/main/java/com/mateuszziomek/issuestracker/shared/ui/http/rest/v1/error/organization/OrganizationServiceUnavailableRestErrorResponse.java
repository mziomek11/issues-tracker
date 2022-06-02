package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class OrganizationServiceUnavailableRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.ORGANIZATION_SERVICE_UNAVAILABLE;
    private static final HttpStatus STATUS = HttpStatus.SERVICE_UNAVAILABLE;
    private static final String MESSAGE = "Organization service unavailable";

    public static ResponseEntity<RestErrorResponse> asResponseEntity() {
        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, MESSAGE));
    }

    private OrganizationServiceUnavailableRestErrorResponse() {}
}
