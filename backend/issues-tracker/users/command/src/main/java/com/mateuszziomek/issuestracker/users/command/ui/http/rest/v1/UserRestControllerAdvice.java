package com.mateuszziomek.issuestracker.users.command.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserEmailUnavailableException;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserServiceUnavailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserActivationTokenMismatchException;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserAlreadyActivatedException;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserNotFoundException;
import com.mateuszziomek.rest.v1.RestErrorResponse;
import com.mateuszziomek.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserRestControllerAdvice {
    @ExceptionHandler(RestValidationException.class)
    public ResponseEntity<RestErrorResponse> handle(RestValidationException ex) {
        var errorResponse = new RestErrorResponse("Validation failed", ex.getErrors());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(UserEmailUnavailableException.class)
    public ResponseEntity<RestErrorResponse> handle(UserEmailUnavailableException ex) {
        var errorResponse = new RestErrorResponse("User email already taken");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(UserActivationTokenMismatchException.class)
    public ResponseEntity<RestErrorResponse> handle(UserActivationTokenMismatchException ex) {
        var errorResponse = new RestErrorResponse("User activation token is not valid");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyActivatedException.class)
    public ResponseEntity<RestErrorResponse> handle(UserAlreadyActivatedException ex) {
        var errorResponse = new RestErrorResponse("User already activated");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(UserNotFoundException ex) {
        var errorResponse = new RestErrorResponse("User not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(UserServiceUnavailableException.class)
    public ResponseEntity<RestErrorResponse> handle(UserServiceUnavailableException ex) {
        var errorResponse = new RestErrorResponse("Service unavailable");

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(errorResponse);
    }
}
