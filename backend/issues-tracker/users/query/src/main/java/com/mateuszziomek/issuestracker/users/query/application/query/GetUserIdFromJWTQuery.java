package com.mateuszziomek.issuestracker.users.query.application.query;

import com.mateuszziomek.cqrs.query.Query;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserIdFromJWTQuery extends Query {
    private final String jwt;
}
