package org.example.issuestracker.issues.command.application.command;

import jakarta.validation.constraints.NotNull;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.common.domain.issue.IssueType;

import java.util.UUID;

public class ChangeIssueTypeCommand {
    private final IssueId issueId;
    private final IssueType issueType;

    public static ChangeIssueTypeCommandBuilder builder() {
        return new ChangeIssueTypeCommandBuilder();
    }

    private ChangeIssueTypeCommand(IssueId issueId, IssueType issueType) {
        this.issueId = issueId;
        this.issueType = issueType;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public static class ChangeIssueTypeCommandBuilder
            extends CommandBuilder<ChangeIssueTypeCommandBuilder, ChangeIssueTypeCommand> {
        @NotNull
        private UUID issueId;

        @NotNull
        private IssueType issueType;

        public ChangeIssueTypeCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
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
                    issueType
            );
        }
    }
}
