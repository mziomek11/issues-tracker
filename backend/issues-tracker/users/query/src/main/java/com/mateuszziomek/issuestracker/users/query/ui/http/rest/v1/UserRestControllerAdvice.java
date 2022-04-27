package com.mateuszziomek.issuestracker.users.query.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.users.query.application.query.exception.InvalidCredentialsException;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.exception.InvalidJWTException;
import com.mateuszziomek.rest.v1.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserRestControllerAdvice {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<RestErrorResponse> handle(InvalidCredentialsException ex) {
        var errorResponse = new RestErrorResponse("Invalid username or password");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(InvalidJWTException.class)
    public ResponseEntity<RestErrorResponse> handle(InvalidJWTException ex) {
        var errorResponse = new RestErrorResponse("Invalid jwt");

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }
}
