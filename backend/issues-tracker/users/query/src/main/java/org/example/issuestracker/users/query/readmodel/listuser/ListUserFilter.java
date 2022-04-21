package org.example.issuestracker.users.query.readmodel.listuser;

import lombok.Builder;
import lombok.Getter;
import org.example.issuestracker.shared.domain.valueobject.UserStatus;

@Getter
@Builder
public class ListUserFilter {
    private final String email;
    private final UserStatus status;
}
