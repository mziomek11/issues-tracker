package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestErrorResponse {
    private String message;
    private int status;
    private String code;
    private Map<String, ? extends Object> details;

    public RestErrorResponse(ApplicationErrorCode code, HttpStatus status, String message) {
        this.code = code.code();
        this.status = status.value();
        this.message = message;
    }

    public RestErrorResponse(ApplicationErrorCode code, HttpStatus status, String message, Map<String, ? extends Object> details) {
        this.code = code.code();
        this.status = status.value();
        this.message = message;
        this.details = details;
    }

    public Map<String, Object> toAttributeMap() {
        return Map.of("message", message);
    }
}
