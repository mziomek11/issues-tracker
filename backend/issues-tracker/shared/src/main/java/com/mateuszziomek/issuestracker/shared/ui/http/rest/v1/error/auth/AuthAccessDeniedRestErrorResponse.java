package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.auth;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthAccessDeniedRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.AUTH_ACCESS_DENIED;
    private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;
    private static final String MESSAGE = "Access denied";

    public static ResponseEntity<RestErrorResponse> asResponseEntity() {
        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, MESSAGE));
    }

    private AuthAccessDeniedRestErrorResponse() {}
}
