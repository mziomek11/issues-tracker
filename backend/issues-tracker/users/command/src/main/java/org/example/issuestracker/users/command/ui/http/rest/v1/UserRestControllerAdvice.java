package org.example.issuestracker.users.command.ui.http.rest.v1;

import org.example.issuestracker.users.command.application.gateway.exception.UserEmailNotAvailableException;
import org.example.issuestracker.users.command.domain.account.exception.UserActivationTokenMismatchException;
import org.example.issuestracker.users.command.domain.account.exception.UserAlreadyActivatedException;
import org.example.issuestracker.users.command.domain.account.exception.UserNotFoundException;
import org.example.rest.v1.RestErrorResponse;
import org.example.rest.v1.RestValidationException;
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

    @ExceptionHandler(UserEmailNotAvailableException.class)
    public ResponseEntity<RestErrorResponse> handle(UserEmailNotAvailableException ex) {
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
}
