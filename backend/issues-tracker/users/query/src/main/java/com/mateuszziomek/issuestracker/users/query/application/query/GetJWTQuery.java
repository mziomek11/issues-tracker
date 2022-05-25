package com.mateuszziomek.issuestracker.users.query.application.query;

import com.mateuszziomek.cqrs.query.Query;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetJWTQuery extends Query<String> {
    private final String email;
    private final String password;
}
