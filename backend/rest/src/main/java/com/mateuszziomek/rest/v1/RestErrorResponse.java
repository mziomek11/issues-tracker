package com.mateuszziomek.rest.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestErrorResponse {
    private String message;
    private Map<String, Set<String>> errors;

    public RestErrorResponse(String message) {
        this.message = message;
    }

    public RestErrorResponse(Map<String, Set<String>> errors) {
        this.errors = errors;
    }

    public RestErrorResponse(String message, Map<String, Set<String>> errors) {
        this.message = message;
        this.errors = errors;
    }

    public Map<String, Object> toAttributeMap() {
        return Map.of("message", message);
    }
}
