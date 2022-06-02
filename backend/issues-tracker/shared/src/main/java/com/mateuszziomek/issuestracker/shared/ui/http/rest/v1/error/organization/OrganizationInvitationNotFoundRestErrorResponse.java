package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.organization;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public class OrganizationInvitationNotFoundRestErrorResponse {
    private static final ApplicationErrorCode CODE = ApplicationErrorCode.ORGANIZATION_INVITATION_NOT_FOUND;
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "Invitation for member with id %s not found";

    public static ResponseEntity<RestErrorResponse> asResponseEntity(UUID memberId) {
        var details = Map.of("memberId", memberId);

        return ResponseEntity
                .status(STATUS)
                .body(new RestErrorResponse(CODE, STATUS, String.format(MESSAGE, memberId), details));
    }

    private OrganizationInvitationNotFoundRestErrorResponse() {}
}
