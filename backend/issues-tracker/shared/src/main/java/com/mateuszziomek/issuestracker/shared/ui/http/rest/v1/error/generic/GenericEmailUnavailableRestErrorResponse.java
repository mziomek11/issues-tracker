package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.generic;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GenericEmailUnavailableRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.GENERIC_EMAIL_UNAVAILABLE;
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;
    private static final String MESSAGE = "Email unavailable";

    public static ResponseEntity<RestErrorResponse> asResponseEntity() {
        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, MESSAGE));
    }

    private GenericEmailUnavailableRestErrorResponse() {}
}
