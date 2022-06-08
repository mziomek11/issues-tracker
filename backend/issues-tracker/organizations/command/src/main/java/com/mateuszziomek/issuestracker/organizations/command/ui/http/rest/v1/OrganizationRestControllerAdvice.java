package com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.organizations.command.application.service.exception.MemberNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.exception.MemberAlreadyPresentException;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationOwnerNotValidException;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.generic.GenericValidationFailedRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization.*;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.user.UserNotFoundRestErrorResponse;
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

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationNotFoundException ex) {
        return OrganizationNotFoundRestErrorResponse.asResponseEntity(ex.getOrganizationId().getValue());
    }

    @ExceptionHandler(OrganizationOwnerNotValidException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationOwnerNotValidException ex) {
        return OrganizationOwnerInvalidRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(InvitationNotFoundException ex) {
        return OrganizationInvitationNotFoundRestErrorResponse.asResponseEntity(ex.getMemberId().getValue());
    }

    @ExceptionHandler(InvitationAlreadyPresentException.class)
    public ResponseEntity<RestErrorResponse> handle(InvitationAlreadyPresentException ex) {
        return OrganizationInvitationAlreadyPresentRestErrorResponse.asResponseEntity(ex.getMemberId().getValue());
    }

    @ExceptionHandler(MemberAlreadyPresentException.class)
    public ResponseEntity<RestErrorResponse> handle(MemberAlreadyPresentException ex) {
        return OrganizationMemberAlreadyPresentRestErrorResponse.asResponseEntity(ex.getMemberId().getValue());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(MemberNotFoundException ex) {
        return UserNotFoundRestErrorResponse.asResponseEntity();
    }
}
