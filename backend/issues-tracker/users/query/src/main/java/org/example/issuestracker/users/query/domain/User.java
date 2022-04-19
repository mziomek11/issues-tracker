package org.example.issuestracker.users.query.domain;

import lombok.Builder;
import lombok.Data;
import org.example.issuestracker.shared.domain.valueobject.UserStatus;

import java.util.UUID;

@Data
@Builder
public class User {
    private UUID id;
    private String email;
    private String password;
    private UserStatus status;
}
