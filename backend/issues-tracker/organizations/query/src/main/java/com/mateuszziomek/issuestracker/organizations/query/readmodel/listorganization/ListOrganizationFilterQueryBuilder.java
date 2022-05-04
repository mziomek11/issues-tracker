package com.mateuszziomek.issuestracker.organizations.query.readmodel.listorganization;

import com.mateuszziomek.issuestracker.shared.readmodel.ListOrganization;

import java.util.List;
import java.util.UUID;

public interface ListOrganizationFilterQueryBuilder {
    ListOrganizationFilterQueryBuilder containsMemberWithId(UUID memberId);
    List<ListOrganization> execute();
}
