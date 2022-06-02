package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.issue;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class IssueVoteAlreadyExistsRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.ISSUE_VOTE_ALREADY_EXISTS;
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;
    private static final String MESSAGE = "Vote already exists";

    public static ResponseEntity<RestErrorResponse> asResponseEntity() {
        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, MESSAGE));
    }

    private IssueVoteAlreadyExistsRestErrorResponse() {}
}
