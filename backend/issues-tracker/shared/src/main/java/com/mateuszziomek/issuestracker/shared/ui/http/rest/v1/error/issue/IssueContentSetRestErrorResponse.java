package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.issue;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class IssueContentSetRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.ISSUE_CONTENT_SET;
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;
    private static final String MESSAGE = "Issue already has given content";

    public static ResponseEntity<RestErrorResponse> asResponseEntity() {
        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, MESSAGE));
    }

    private IssueContentSetRestErrorResponse() {}
}
