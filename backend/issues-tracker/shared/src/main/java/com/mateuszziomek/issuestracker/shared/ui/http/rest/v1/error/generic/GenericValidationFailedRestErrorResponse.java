package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.generic;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

public class GenericValidationFailedRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.GENERIC_VALIDATION_FAILED;
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "Validation failed";

    public static ResponseEntity<RestErrorResponse> asResponseEntity(Map<String, Set<String>> errors) {
        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, MESSAGE, errors));
    }

    private GenericValidationFailedRestErrorResponse() {}
}
