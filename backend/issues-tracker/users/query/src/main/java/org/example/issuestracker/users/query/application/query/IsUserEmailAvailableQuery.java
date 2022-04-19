package org.example.issuestracker.users.query.application.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.query.Query;

@RequiredArgsConstructor
@Getter
public class IsUserEmailAvailableQuery extends Query {
    private final String userEmail;
}
