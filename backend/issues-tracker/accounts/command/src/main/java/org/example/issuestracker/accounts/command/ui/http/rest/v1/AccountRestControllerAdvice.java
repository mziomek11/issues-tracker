package org.example.issuestracker.accounts.command.ui.http.rest.v1;

import org.example.issuestracker.accounts.command.application.gateway.exception.AccountEmailAlreadyTakenException;
import org.example.issuestracker.accounts.command.domain.account.exception.AccountActivationTokenMismatchException;
import org.example.issuestracker.accounts.command.domain.account.exception.AccountAlreadyActivatedException;
import org.example.issuestracker.accounts.command.domain.account.exception.AccountNotFoundException;
import org.example.rest.v1.RestErrorResponse;
import org.example.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountRestControllerAdvice {
    @ExceptionHandler(RestValidationException.class)
    public ResponseEntity<RestErrorResponse> handle(RestValidationException ex) {
        var errorResponse = new RestErrorResponse("Validation failed", ex.getErrors());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(AccountEmailAlreadyTakenException.class)
    public ResponseEntity<RestErrorResponse> handle(AccountEmailAlreadyTakenException ex) {
        var errorResponse = new RestErrorResponse("Account email already taken");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(AccountActivationTokenMismatchException.class)
    public ResponseEntity<RestErrorResponse> handle(AccountActivationTokenMismatchException ex) {
        var errorResponse = new RestErrorResponse("Account activation token is not valid");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(AccountAlreadyActivatedException.class)
    public ResponseEntity<RestErrorResponse> handle(AccountAlreadyActivatedException ex) {
        var errorResponse = new RestErrorResponse("Account already activated");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(AccountNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Account not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }
}
