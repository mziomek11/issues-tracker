package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.*;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.generic.GenericValidationFailedRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.issue.*;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization.OrganizationAccessDeniedRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization.OrganizationNotFoundRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization.OrganizationProjectNotFoundRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization.OrganizationServiceUnavailableRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IssueRestControllerAdvice {
    @ExceptionHandler(RestValidationException.class)
    public ResponseEntity<RestErrorResponse> handle(RestValidationException ex) {
        return GenericValidationFailedRestErrorResponse.asResponseEntity(ex.getErrors());
    }

    @ExceptionHandler(IssueNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueNotFoundException ex) {
        return IssueNotFoundRestErrorResponse.asResponseEntity(ex.getIssueId().getValue());
    }

    @ExceptionHandler(IssueClosedException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueClosedException ex) {
        return IssueClosedRestErrorResponse.asResponseEntity(ex.getIssueId().getValue());
    }

    @ExceptionHandler(IssueTypeSetException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueTypeSetException ex) {
        return IssueTypeSetRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(IssueContentSetException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueContentSetException ex) {
        return IssueContentSetRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(IssueNameSetException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueNameSetException ex) {
        return IssueNameSetRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(VoteAlreadyExistsException.class)
    public ResponseEntity<RestErrorResponse> handle(VoteAlreadyExistsException ex) {
        return IssueVoteAlreadyExistsRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(CommentNotFoundException ex) {
        return IssueCommentNotFoundRestErrorResponse.asResponseEntity(ex.getCommentId().getValue());
    }

    @ExceptionHandler(CommentHiddenException.class)
    public ResponseEntity<RestErrorResponse> handle(CommentHiddenException ex) {
        return IssueCommentHiddenRestErrorResponse.asResponseEntity(ex.getCommentId().getValue());
    }

    @ExceptionHandler(CommentContentSetException.class)
    public ResponseEntity<RestErrorResponse> handle(CommentContentSetException ex) {
        return IssueCommentContentSetRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationNotFoundException ex) {
        return OrganizationNotFoundRestErrorResponse.asResponseEntity(ex.getOrganizationId().getValue());
    }

    @ExceptionHandler(OrganizationProjectNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationProjectNotFoundException ex) {
        return OrganizationProjectNotFoundRestErrorResponse.asResponseEntity(ex.getOrganizationProjectId().getValue());
    }

    @ExceptionHandler(OrganizationMemberNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationMemberNotFoundException ex) {
        return OrganizationAccessDeniedRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(OrganizationServiceUnavailableException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationServiceUnavailableException ex) {
        return OrganizationServiceUnavailableRestErrorResponse.asResponseEntity();
    }
}
