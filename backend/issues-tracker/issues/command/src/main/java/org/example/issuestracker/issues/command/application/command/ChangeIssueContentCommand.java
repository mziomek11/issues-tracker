package org.example.issuestracker.issues.command.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public class ChangeIssueContentCommand {
    private final IssueId issueId;
    private final IssueContent issueContent;

    public static ChangeIssueContentCommandBuilder builder() {
        return new ChangeIssueContentCommandBuilder();
    }

    private ChangeIssueContentCommand(IssueId issueId, IssueContent issueContent) {
        this.issueId = issueId;
        this.issueContent = issueContent;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public IssueContent getIssueContent() {
        return issueContent;
    }

    public static class ChangeIssueContentCommandBuilder
            extends CommandBuilder<ChangeIssueContentCommandBuilder, ChangeIssueContentCommand> {
        @NotNull
        private UUID issueId;

        @NotBlank
        private String issueContent;

        public ChangeIssueContentCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public ChangeIssueContentCommandBuilder issueContent(String issueContent) {
            this.issueContent = issueContent;
            return this;
        }

        @Override
        protected ChangeIssueContentCommand create() {
            return new ChangeIssueContentCommand(
                    new IssueId(issueId),
                    new IssueContent(issueContent)
            );
        }
    }
}
