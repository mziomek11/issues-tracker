package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import org.example.issuestracker.shared.domain.valueobject.IssueType;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ChangeIssueTypeCommand {
    private final IssueId issueId;
    private final IssueType issueType;
    private final IssueOrganizationDetails organizationDetails;

    public static ChangeIssueTypeCommandBuilder builder() {
        return new ChangeIssueTypeCommandBuilder();
    }

    public static class ChangeIssueTypeCommandBuilder
            extends CommandBuilder<ChangeIssueTypeCommandBuilder, ChangeIssueTypeCommand> {
        public static final String ISSUE_TYPE_FIELD_NAME = "issueType";

        @NotNull
        private UUID issueId;

        @NotNull
        private IssueType issueType;

        @NotNull
        private IssueOrganizationDetails organizationDetails;

        public ChangeIssueTypeCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public ChangeIssueTypeCommandBuilder issueType(IssueType issueType) {
            this.issueType = issueType;
            return this;
        }

        public ChangeIssueTypeCommandBuilder organizationDetails(IssueOrganizationDetails organizationDetails) {
            this.organizationDetails = organizationDetails;
            return this;
        }

        @Override
        protected ChangeIssueTypeCommand create() {
            return new ChangeIssueTypeCommand(
                    new IssueId(issueId),
                    issueType,
                    organizationDetails
            );
        }
    }
}
