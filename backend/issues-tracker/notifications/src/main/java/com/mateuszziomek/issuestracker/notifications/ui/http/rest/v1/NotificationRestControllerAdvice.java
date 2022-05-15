package com.mateuszziomek.issuestracker.notifications.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.shared.infrastructure.security.exception.AccessDeniedException;
import com.mateuszziomek.rest.v1.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotificationRestControllerAdvice {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestErrorResponse> handle(AccessDeniedException ex) {
        var errorResponse = new RestErrorResponse("Access denied");

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }
}
