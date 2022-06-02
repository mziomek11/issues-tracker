package com.mateuszziomek.issuestracker.issues.query.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.issues.query.application.query.exception.IssueNotFoundException;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.generic.GenericValidationFailedRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.issue.IssueNotFoundRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization.OrganizationAccessDeniedRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization.OrganizationInvitationNotFoundRestErrorResponse;
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

    @ExceptionHandler(OrganizationMemberNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationMemberNotFoundException ex) {
        return OrganizationAccessDeniedRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationNotFoundException ex) {
        return OrganizationInvitationNotFoundRestErrorResponse.asResponseEntity(ex.getOrganizationId());
    }

    @ExceptionHandler(OrganizationProjectNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationProjectNotFoundException ex) {
        return OrganizationProjectNotFoundRestErrorResponse.asResponseEntity(ex.getOrganizationProjectId());
    }

    @ExceptionHandler(OrganizationServiceUnavailableException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationServiceUnavailableException ex) {
        return OrganizationServiceUnavailableRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(IssueNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueNotFoundException ex) {
        return IssueNotFoundRestErrorResponse.asResponseEntity(ex.getIssueId());
    }
}
