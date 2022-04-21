package org.example.issuestracker.organizations.query.application.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.query.Query;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GetDetailsOrganizationQuery extends Query {
    private final UUID organizationId;
}
