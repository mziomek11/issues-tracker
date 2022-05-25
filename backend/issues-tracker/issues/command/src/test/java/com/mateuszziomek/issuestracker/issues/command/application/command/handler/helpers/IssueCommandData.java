package com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers;

import com.mateuszziomek.issuestracker.issues.command.application.command.OpenIssueCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;

import java.util.UUID;

public class IssueCommandData {
    public final static UUID ORGANIZATION_UUID = UUID.randomUUID();
    public final static UUID PROJECT_UUID = UUID.randomUUID();
    public final static UUID MEMBER_UUID = UUID.randomUUID();
    public final static UUID ISSUE_UUID = UUID.randomUUID();
    public final static OrganizationId ORGANIZATION_ID = new OrganizationId(ORGANIZATION_UUID);
    public final static OrganizationProjectId PROJECT_ID = new OrganizationProjectId(PROJECT_UUID);
    public final static OrganizationMemberId MEMBER_ID = new OrganizationMemberId(MEMBER_UUID);

    public final static IssueOrganizationDetails ORGANIZATION_DETAILS = new IssueOrganizationDetails(
            ORGANIZATION_ID,
            PROJECT_ID,
            MEMBER_ID
    );

    public final static OpenIssueCommand OPEN_ISSUE_COMMAND = OpenIssueCommand
            .builder()
            .issueId(ISSUE_UUID)
            .issueContent("Example content")
            .issueName("example name")
            .issueType(IssueType.ENHANCEMENT)
            .organizationDetails(ORGANIZATION_DETAILS)
            .build();
}
