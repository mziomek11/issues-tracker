package com.mateuszziomek.issuestracker.organizations.query.readmodel.listorganization;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ListOrganizationFilter {
    private final UUID memberId;
}
