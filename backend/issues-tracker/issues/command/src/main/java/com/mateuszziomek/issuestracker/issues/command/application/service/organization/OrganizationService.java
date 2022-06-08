package com.mateuszziomek.issuestracker.issues.command.application.service.organization;

import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import com.mateuszziomek.issuestracker.issues.command.projection.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    /**
     * @throws OrganizationMemberNotFoundException if member with given id does not exist in organization
     * @throws OrganizationNotFoundException if organization with given id does not exist
     * @throws OrganizationProjectNotFoundException if project with given id does not exist in organization
     */
    public void ensureOrganizationHasProjectAndMember(IssueOrganizationDetails organizationDetails) {
        var organizationId = organizationDetails.organizationId().getValue();
        var projectId = organizationDetails.projectId().getValue();
        var memberId = organizationDetails.memberId().getValue();

        var organization = organizationRepository
                .findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(new OrganizationId(organizationId)));

        if (!organization.getProjectIds().contains(projectId)) {
           throw new OrganizationProjectNotFoundException(new OrganizationProjectId(projectId));
        }

        if (!organization.getMemberIds().contains(memberId)) {
           throw new OrganizationMemberNotFoundException(new OrganizationMemberId(memberId));
        }
    }
}
