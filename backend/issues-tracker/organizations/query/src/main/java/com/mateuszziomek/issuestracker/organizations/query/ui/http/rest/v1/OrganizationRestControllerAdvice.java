package com.mateuszziomek.issuestracker.organizations.query.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.organizations.query.application.query.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.exception.AccessDeniedException;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.auth.AuthAccessDeniedRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.generic.GenericValidationFailedRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization.OrganizationInvitationNotFoundRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrganizationRestControllerAdvice {
    @ExceptionHandler(RestValidationException.class)
    public ResponseEntity<RestErrorResponse> handle(RestValidationException ex) {
        return GenericValidationFailedRestErrorResponse.asResponseEntity(ex.getErrors());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestErrorResponse> handle(AccessDeniedException ex) {
        return AuthAccessDeniedRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationNotFoundException ex) {
        return OrganizationInvitationNotFoundRestErrorResponse.asResponseEntity(ex.getOrganizationId());
    }
}
