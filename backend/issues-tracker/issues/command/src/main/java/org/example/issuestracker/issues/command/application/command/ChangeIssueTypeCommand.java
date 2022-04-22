package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import org.example.issuestracker.shared.domain.valueobject.IssueType;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ChangeIssueTypeCommand {
    private final IssueId issueId;
    private final OrganizationId organizationId;
    private final OrganizationProjectId projectId;
    private final OrganizationMemberId memberId;
    private final IssueType issueType;

    public static ChangeIssueTypeCommandBuilder builder() {
        return new ChangeIssueTypeCommandBuilder();
    }

    public static class ChangeIssueTypeCommandBuilder
            extends CommandBuilder<ChangeIssueTypeCommandBuilder, ChangeIssueTypeCommand> {
        public static final String ISSUE_TYPE_FIELD_NAME = "issueType";

        @NotNull
        private UUID issueId;

        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID projectId;

        @NotNull
        private UUID memberId;

        @NotNull
        private IssueType issueType;

        public ChangeIssueTypeCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public ChangeIssueTypeCommandBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public ChangeIssueTypeCommandBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public ChangeIssueTypeCommandBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

        public ChangeIssueTypeCommandBuilder issueType(IssueType issueType) {
            this.issueType = issueType;
            return this;
        }

        @Override
        protected ChangeIssueTypeCommand create() {
            return new ChangeIssueTypeCommand(
                    new IssueId(issueId),
                    new OrganizationId(organizationId),
                    new OrganizationProjectId(projectId),
                    new OrganizationMemberId(memberId),
                    issueType
            );
        }
    }
}
