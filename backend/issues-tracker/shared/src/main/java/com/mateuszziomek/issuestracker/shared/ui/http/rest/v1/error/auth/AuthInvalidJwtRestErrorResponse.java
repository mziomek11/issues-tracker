package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.auth;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthInvalidJwtRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.AUTH_INVALID_JWT;
    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;
    private static final String MESSAGE = "Invalid JWT";

    public static ResponseEntity<RestErrorResponse> asResponseEntity() {
        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, MESSAGE));
    }

    private AuthInvalidJwtRestErrorResponse() {}
}
