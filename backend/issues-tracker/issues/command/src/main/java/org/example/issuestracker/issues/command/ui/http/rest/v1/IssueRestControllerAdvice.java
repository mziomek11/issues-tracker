package org.example.issuestracker.issues.command.ui.http.rest.v1;

import org.example.issuestracker.issues.command.domain.comment.exception.CommentWithIdExistsException;
import org.example.issuestracker.issues.command.domain.issue.exception.*;
import org.example.rest.v1.RestErrorResponse;
import org.example.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IssueRestControllerAdvice {
    @ExceptionHandler(RestValidationException.class)
    public ResponseEntity<RestErrorResponse> handleRestValidationException(RestValidationException ex) {
        var errorResponse = new RestErrorResponse("Validation failed", ex.getErrors());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleIssueNotFoundException(IssueNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Issue not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueClosedException.class)
    public ResponseEntity<RestErrorResponse> handleIssueClosedException(IssueClosedException ex) {
        var errorResponse = new RestErrorResponse("Issue is closed");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueTypeSetException.class)
    public ResponseEntity<RestErrorResponse> handleIssueTypeSetException(IssueTypeSetException ex) {
        var errorResponse = new RestErrorResponse("Issue type is already set");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueContentSetException.class)
    public ResponseEntity<RestErrorResponse> handleIssueContentSetException(IssueContentSetException ex) {
        var errorResponse = new RestErrorResponse("Issue content is already set");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueNameSetException.class)
    public ResponseEntity<RestErrorResponse> handleIssueNameSetException(IssueNameSetException ex) {
        var errorResponse = new RestErrorResponse("Issue name is already set");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(CommentWithIdExistsException.class)
    public ResponseEntity<RestErrorResponse> handleCommentWithIdExistsException(CommentWithIdExistsException ex) {
        var errorResponse = new RestErrorResponse("Comment with id already exist");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }
}
