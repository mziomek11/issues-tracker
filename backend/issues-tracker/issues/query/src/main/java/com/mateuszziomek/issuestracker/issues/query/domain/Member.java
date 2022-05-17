package com.mateuszziomek.issuestracker.issues.query.domain;

import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Member {
    private UUID id;
    private String email;

    public static Member create(UserRegisteredEvent event) {
        return new Member(event.getId(), event.getUserEmail());
    }
}
