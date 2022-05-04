package com.mateuszziomek.issuestracker.organizations.query.application.query;

import com.mateuszziomek.cqrs.query.Query;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GetListOrganizationsQuery extends Query {
    private final UUID memberId;
}
