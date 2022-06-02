package com.mateuszziomek.issuestracker.users.query.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.shared.infrastructure.security.exception.AccessDeniedException;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.auth.AuthAccessDeniedRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.auth.AuthInvalidCredentialsRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.auth.AuthInvalidJwtRestErrorResponse;
import com.mateuszziomek.issuestracker.users.query.application.query.exception.InvalidCredentialsException;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.exception.InvalidJWTException;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserRestControllerAdvice {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestErrorResponse> handle(AccessDeniedException ex) {
        return AuthAccessDeniedRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<RestErrorResponse> handle(InvalidCredentialsException ex) {
        return AuthInvalidCredentialsRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(InvalidJWTException.class)
    public ResponseEntity<RestErrorResponse> handle(InvalidJWTException ex) {
        return AuthInvalidJwtRestErrorResponse.asResponseEntity();
    }
}
