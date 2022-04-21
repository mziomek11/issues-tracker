package org.example.issuestracker.organizations.command.application.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.organizations.command.domain.member.MemberId;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationId;
import org.example.issuestracker.organizations.command.domain.project.ProjectId;
import org.example.issuestracker.organizations.command.domain.project.ProjectName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CreateOrganizationProjectCommand {
    private final OrganizationId organizationId;
    private final MemberId organizationOwnerId;
    private final ProjectId projectId;
    private final ProjectName projectName;

    public static CreateOrganizationProjectCommandBuilder builder() {
        return new CreateOrganizationProjectCommandBuilder();
    }

    public static class CreateOrganizationProjectCommandBuilder
            extends CommandBuilder<CreateOrganizationProjectCommandBuilder, CreateOrganizationProjectCommand> {
        public static final String PROJECT_NAME_FIELD_NAME = "projectName";

        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID organizationOwnerId;

        @NotNull
        private UUID projectId;

        @NotBlank
        private String projectName;

        public CreateOrganizationProjectCommandBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public CreateOrganizationProjectCommandBuilder organizationOwnerId(UUID organizationOwnerId) {
            this.organizationOwnerId = organizationOwnerId;
            return this;
        }

        public CreateOrganizationProjectCommandBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public CreateOrganizationProjectCommandBuilder projectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        @Override
        protected CreateOrganizationProjectCommand create() {
            return new CreateOrganizationProjectCommand(
                    new OrganizationId(organizationId),
                    new MemberId(organizationOwnerId),
                    new ProjectId(projectId),
                    new ProjectName(projectName)
            );
        }
    }
}
