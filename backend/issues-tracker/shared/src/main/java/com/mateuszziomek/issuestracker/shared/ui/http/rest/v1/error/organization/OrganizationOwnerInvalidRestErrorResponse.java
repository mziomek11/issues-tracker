package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class OrganizationOwnerInvalidRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.ORGANIZATION_OWNER_INVALID;
    private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;
    private static final String MESSAGE = "Only organization owner is allowed to perform this action";

    public static ResponseEntity<RestErrorResponse> asResponseEntity() {
        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, MESSAGE));
    }

    private OrganizationOwnerInvalidRestErrorResponse() {}
}
