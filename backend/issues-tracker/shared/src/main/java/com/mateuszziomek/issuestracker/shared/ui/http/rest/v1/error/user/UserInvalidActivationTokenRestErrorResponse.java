package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.user;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserInvalidActivationTokenRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.USER_INVALID_ACTIVATION_TOKEN;
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;
    private static final String MESSAGE = "Invalid activation token";

    public static ResponseEntity<RestErrorResponse> asResponseEntity() {
        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, MESSAGE));
    }

    private UserInvalidActivationTokenRestErrorResponse() {}
}
