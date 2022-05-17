package com.mateuszziomek.issuestracker.issues.query.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.rest.v1.RestErrorResponse;
import com.mateuszziomek.rest.v1.RestValidationException;
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

    @ExceptionHandler(OrganizationMemberNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationMemberNotFoundException ex) {
        var errorResponse = new RestErrorResponse("User is not in organization");

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Organization not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(OrganizationProjectNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationProjectNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Project not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(OrganizationServiceUnavailableException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationServiceUnavailableException ex) {
        var errorResponse = new RestErrorResponse("Service unavailable");

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(errorResponse);
    }
}
