package com.mateuszziomek.issuestracker.users.query.application.query;

import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserIdFromJWTQuery extends Query {
    private final String jwt;
    private final UserRole userRole;
}
