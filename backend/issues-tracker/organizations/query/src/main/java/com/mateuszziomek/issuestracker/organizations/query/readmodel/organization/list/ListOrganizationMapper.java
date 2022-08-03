package com.mateuszziomek.issuestracker.organizations.query.readmodel.organization.list;

import com.mateuszziomek.issuestracker.organizations.query.domain.member.Member;
import com.mateuszziomek.issuestracker.organizations.query.domain.organization.Organization;
import com.mateuszziomek.issuestracker.organizations.query.domain.Project;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.ListOrganization;

import java.util.List;

public class ListOrganizationMapper {
    private ListOrganizationMapper() {}

    public static ListOrganization fromModel(Organization organization) {
        return ListOrganization
                .builder()
                .id(organization.getId())
                .name(organization.getName())
                .ownerId(organization.getOwnerId())
                .members(mapMembers(organization.getMembers()))
                .projects(mapProjects(organization.getProjects()))
                .build();
    }

    private static List<ListOrganization.Member> mapMembers(List<Member> members) {
        return members
                .stream()
                .map(ListOrganizationMapper::mapMember)
                .toList();
    }

    private static ListOrganization.Member mapMember(Member member) {
        return ListOrganization.Member
                .builder()
                .id(member.getId())
                .build();
    }

    private static List<ListOrganization.Project> mapProjects(List<Project> projects) {
        return projects
                .stream()
                .map(ListOrganizationMapper::mapProject)
                .toList();
    }

    private static ListOrganization.Project mapProject(Project project) {
        return ListOrganization.Project
                .builder()
                .id(project.getId())
                .name(project.getName())
                .build();
    }
}
