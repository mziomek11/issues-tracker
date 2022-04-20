package org.example.issuestracker.organizations.command.application.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.organizations.command.domain.member.MemberEmail;
import org.example.issuestracker.organizations.command.domain.member.MemberId;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class InviteOrganizationMemberCommand {
    private final OrganizationId organizationId;
    private final MemberId organizationOwnerId;
    private final MemberEmail memberEmail;

    public static InviteOrganizationMemberCommandBuilder builder() {
        return new InviteOrganizationMemberCommandBuilder();
    }

    public static class InviteOrganizationMemberCommandBuilder
            extends CommandBuilder<InviteOrganizationMemberCommandBuilder, InviteOrganizationMemberCommand> {
        public static final String ORGANIZATION_OWNER_ID_FIELD_NAME = "organizationOwnerId";
        public static final String MEMBER_EMAIL_FIELD_NAME = "memberEmail";

        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID organizationOwnerId;

        @NotBlank
        @Email
        private String memberEmail;

        public InviteOrganizationMemberCommandBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public InviteOrganizationMemberCommandBuilder organizationOwnerId(UUID organizationOwnerId) {
            this.organizationOwnerId = organizationOwnerId;
            return this;
        }

        public InviteOrganizationMemberCommandBuilder memberEmail(String memberEmail) {
            this.memberEmail = memberEmail;
            return this;
        }

        @Override
        protected InviteOrganizationMemberCommand create() {
            return new InviteOrganizationMemberCommand(
                    new OrganizationId(organizationId),
                    new MemberId(organizationOwnerId),
                    new MemberEmail(memberEmail)
            );
        }
    }
}
