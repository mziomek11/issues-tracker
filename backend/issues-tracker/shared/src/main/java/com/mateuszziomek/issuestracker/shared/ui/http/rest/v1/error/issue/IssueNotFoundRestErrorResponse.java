package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.issue;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public class IssueNotFoundRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.ISSUE_NOT_FOUND;
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "Issue with id %s does not exist";

    public static ResponseEntity<RestErrorResponse> asResponseEntity(UUID issueId) {
        var details = Map.of("issueId", issueId);

        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, String.format(MESSAGE, issueId), details));
    }

    private IssueNotFoundRestErrorResponse() {}
}
