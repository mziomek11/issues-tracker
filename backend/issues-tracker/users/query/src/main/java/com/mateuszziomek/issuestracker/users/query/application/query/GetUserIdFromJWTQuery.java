package com.mateuszziomek.issuestracker.users.query.application.query;

import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.readmodel.ObjectId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserIdFromJWTQuery extends Query<ObjectId> {
    private final String jwt;
    private final UserRole userRole;
}
