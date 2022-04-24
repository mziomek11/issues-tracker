package com.mateuszziomek.issuestracker.organizations.command.domain.member;

import java.util.Objects;

public record MemberEmail(String text) {
    public MemberEmail {
        Objects.requireNonNull(text);
    }
}
