package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationProjectId;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class RenameIssueCommand {
    private final IssueId issueId;
    private final OrganizationId organizationId;
    private final OrganizationProjectId organizationProjectId;
    private final OrganizationMemberId organizationMemberId;
    private final IssueName issueName;

    public static RenameIssueCommandBuilder builder() {
        return new RenameIssueCommandBuilder();
    }

    public static class RenameIssueCommandBuilder extends CommandBuilder<RenameIssueCommandBuilder, RenameIssueCommand> {
        public static final String ISSUE_NAME_FIELD_NAME = "issueName";

        @NotNull
        private UUID issueId;

        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID organizationProjectId;

        @NotNull
        private UUID organizationMemberId;

        @NotBlank
        private String issueName;

        public RenameIssueCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public RenameIssueCommandBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public RenameIssueCommandBuilder organizationProjectId(UUID organizationProjectId) {
            this.organizationProjectId = organizationProjectId;
            return this;
        }

        public RenameIssueCommandBuilder organizationMemberId(UUID organizationMemberId) {
            this.organizationMemberId = organizationMemberId;
            return this;
        }

        public RenameIssueCommandBuilder issueName(String issueName) {
            this.issueName = issueName;
            return this;
        }

        @Override
        protected RenameIssueCommand create() {
            return new RenameIssueCommand(
                    new IssueId(issueId),
                    new OrganizationId(organizationId),
                    new OrganizationProjectId(organizationProjectId),
                    new OrganizationMemberId(organizationMemberId),
                    new IssueName(issueName)
            );
        }
    }
}
