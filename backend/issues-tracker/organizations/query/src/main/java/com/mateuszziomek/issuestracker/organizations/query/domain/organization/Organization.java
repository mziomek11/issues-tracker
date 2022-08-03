package com.mateuszziomek.issuestracker.organizations.query.domain.organization;

import com.mateuszziomek.issuestracker.organizations.query.domain.member.Member;
import com.mateuszziomek.issuestracker.organizations.query.domain.Project;
import lombok.Data;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Organization {
    private UUID id;
    private UUID ownerId;
    private String name;
    private List<Project> projects;
    private List<Member> members;

    public static Organization create(OrganizationCreatedEvent organizationCreatedEvent, Member member) {
        var organization = new Organization();

        organization.id = organizationCreatedEvent.getId();
        organization.ownerId = organizationCreatedEvent.getOrganizationOwnerId();
        organization.name = organizationCreatedEvent.getOrganizationName();
        organization.projects = new ArrayList<>();
        organization.members = new ArrayList<>();
        organization.members.add(member);

        return organization;
    }

    public void joinMember(Member member) {
        members.add(member);
    }

    public void addProject(OrganizationProjectCreatedEvent organizationProjectCreatedEvent) {
        var project = new Project(
                organizationProjectCreatedEvent.getProjectId(),
                organizationProjectCreatedEvent.getProjectName()
        );

        projects.add(project);
    }
}
