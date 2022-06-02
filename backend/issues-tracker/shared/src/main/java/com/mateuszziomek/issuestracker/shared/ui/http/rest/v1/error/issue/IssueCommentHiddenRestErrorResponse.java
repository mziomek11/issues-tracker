package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.issue;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public class IssueCommentHiddenRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.ISSUE_COMMENT_HIDDEN;
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;
    private static final String MESSAGE = "Comment with id %s is hidden";

    public static ResponseEntity<RestErrorResponse> asResponseEntity(UUID commentId) {
        var details = Map.of("commentId", commentId);

        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, String.format(MESSAGE, commentId), details));
    }

    private IssueCommentHiddenRestErrorResponse() {}
}
