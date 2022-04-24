package com.mateuszziomek.issuestracker.organizations.command.application.command;

import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CreateOrganizationCommand {
    private final OrganizationId organizationId;
    private final OrganizationName organizationName;
    private final MemberId organizationOwnerId;

    public static CreateOrganizationCommandBuilder builder() {
        return new CreateOrganizationCommandBuilder();
    }

    public static class CreateOrganizationCommandBuilder
            extends CommandBuilder<CreateOrganizationCommandBuilder, CreateOrganizationCommand> {
        public static final String ORGANIZATION_NAME_FIELD_NAME = "organizationName";

        @NotNull
        private UUID organizationId;

        @NotBlank
        private String organizationName;

        @NotNull
        private UUID organizationOwnerId;

        public CreateOrganizationCommandBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public CreateOrganizationCommandBuilder organizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        public CreateOrganizationCommandBuilder organizationOwnerId(UUID organizationOwnerId) {
            this.organizationOwnerId = organizationOwnerId;
            return this;
        }

        @Override
        protected CreateOrganizationCommand create() {
            return new CreateOrganizationCommand(
                    new OrganizationId(organizationId),
                    new OrganizationName(organizationName),
                    new MemberId(organizationOwnerId)
            );
        }
    }
}
