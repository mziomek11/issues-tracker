package com.mateuszziomek.issuestracker.issues.query.application.query.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class IssueNotFoundException extends RuntimeException {
    private final UUID issueId;
}
