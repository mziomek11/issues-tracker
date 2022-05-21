package com.mateuszziomek.issuestracker.users.query.application.query;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import lombok.Builder;
import lombok.Getter;
import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;

@Getter
@Builder
public class GetListUsersQuery extends Query {
    private final String email;
    private final UserStatus status;
    private final UserRole userRole;
}
