package com.mateuszziomek.issuestracker.organizations.command.application.command;

import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class JoinOrganizationMemberCommand {
    private final OrganizationId organizationId;
    private final MemberId memberId;

    public static JoinOrganizationMemberCommandBuilder builder() {
        return new JoinOrganizationMemberCommandBuilder();
    }

    public static class JoinOrganizationMemberCommandBuilder
            extends CommandBuilder<JoinOrganizationMemberCommandBuilder, JoinOrganizationMemberCommand> {
        public static final String MEMBER_ID_FIELD_NAME = "memberId";

        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID memberId;

        public JoinOrganizationMemberCommandBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public JoinOrganizationMemberCommandBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

        @Override
        protected JoinOrganizationMemberCommand create() {
            return new JoinOrganizationMemberCommand(
                    new OrganizationId(organizationId),
                    new MemberId(memberId)
            );
        }
    }
}
