package com.mateuszziomek.issuestracker.users.command.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.generic.GenericEmailUnavailableRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.generic.GenericValidationFailedRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.user.UserInvalidActivationTokenRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.user.UserAlreadyActivatedRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.user.UserNotFoundRestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.user.UserServiceUnavailableRestErrorResponse;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserEmailUnavailableException;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserServiceUnavailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserActivationTokenMismatchException;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserAlreadyActivatedException;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserNotFoundException;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorResponse;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserRestControllerAdvice {
    @ExceptionHandler(RestValidationException.class)
    public ResponseEntity<RestErrorResponse> handle(RestValidationException ex) {
        return GenericValidationFailedRestErrorResponse.asResponseEntity(ex.getErrors());
    }

    @ExceptionHandler(UserEmailUnavailableException.class)
    public ResponseEntity<RestErrorResponse> handle(UserEmailUnavailableException ex) {
        return GenericEmailUnavailableRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(UserActivationTokenMismatchException.class)
    public ResponseEntity<RestErrorResponse> handle(UserActivationTokenMismatchException ex) {
        return UserInvalidActivationTokenRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(UserAlreadyActivatedException.class)
    public ResponseEntity<RestErrorResponse> handle(UserAlreadyActivatedException ex) {
        return UserAlreadyActivatedRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(UserNotFoundException ex) {
        return UserNotFoundRestErrorResponse.asResponseEntity();
    }

    @ExceptionHandler(UserServiceUnavailableException.class)
    public ResponseEntity<RestErrorResponse> handle(UserServiceUnavailableException ex) {
        return UserServiceUnavailableRestErrorResponse.asResponseEntity();
    }
}
