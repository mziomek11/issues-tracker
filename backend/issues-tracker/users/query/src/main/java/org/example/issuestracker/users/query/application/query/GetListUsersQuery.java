package org.example.issuestracker.users.query.application.query;

import lombok.Builder;
import lombok.Getter;
import org.example.cqrs.query.Query;

@Getter
@Builder
public class GetListUsersQuery extends Query {
    private final String email;
}
