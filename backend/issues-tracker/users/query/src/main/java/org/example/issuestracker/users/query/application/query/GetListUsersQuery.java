package org.example.issuestracker.users.query.application.query;

import lombok.Builder;
import lombok.Getter;
import org.example.cqrs.query.Query;
import org.example.issuestracker.shared.domain.valueobject.UserStatus;

@Getter
@Builder
public class GetListUsersQuery extends Query {
    private final String email;
    private final UserStatus status;
}
