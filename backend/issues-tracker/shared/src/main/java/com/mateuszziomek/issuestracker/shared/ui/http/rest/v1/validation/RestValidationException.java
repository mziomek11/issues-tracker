package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class RestValidationException extends IllegalArgumentException {
    private final Map<String, Set<String>> errors;
}
