package org.example.issuestracker.issues.command.ui.http.rest.v1;

import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.IssueCreatorIsNotMemberOfProjectException;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.ProjectNotFoundException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentWithIdExistsException;
import org.example.issuestracker.issues.command.domain.issue.exception.*;
import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import org.example.rest.v1.RestErrorResponse;
import org.example.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IssueRestControllerAdvice {
    @ExceptionHandler(RestValidationException.class)
    public ResponseEntity<RestErrorResponse> handle(RestValidationException ex) {
        var errorResponse = new RestErrorResponse("Validation failed", ex.getErrors());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Issue not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueClosedException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueClosedException ex) {
        var errorResponse = new RestErrorResponse("Issue is closed");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueTypeSetException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueTypeSetException ex) {
        var errorResponse = new RestErrorResponse("Issue type is already set");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueContentSetException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueContentSetException ex) {
        var errorResponse = new RestErrorResponse("Issue content is already set");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueNameSetException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueNameSetException ex) {
        var errorResponse = new RestErrorResponse("Issue name is already set");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(VoteAlreadyExistsException.class)
    public ResponseEntity<RestErrorResponse> handle(VoteAlreadyExistsException ex) {
        var errorResponse = new RestErrorResponse("Vote already exists");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(CommentWithIdExistsException.class)
    public ResponseEntity<RestErrorResponse> handle(CommentWithIdExistsException ex) {
        var errorResponse = new RestErrorResponse("Comment with id already exist");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(CommentNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Comment not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(CommentHiddenException.class)
    public ResponseEntity<RestErrorResponse> handle(CommentHiddenException ex) {
        var errorResponse = new RestErrorResponse("Comment is hidden");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(CommentContentSetException.class)
    public ResponseEntity<RestErrorResponse> handle(CommentContentSetException ex) {
        var errorResponse = new RestErrorResponse("Comment content is already set");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Organization not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(ProjectNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Project not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueCreatorIsNotMemberOfProjectException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueCreatorIsNotMemberOfProjectException ex) {
        var errorResponse = new RestErrorResponse("Issue creator is not member of project");

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }
}
