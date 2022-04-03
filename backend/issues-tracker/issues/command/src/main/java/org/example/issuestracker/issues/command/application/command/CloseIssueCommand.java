package org.example.issuestracker.issues.command.application.command;

import jakarta.validation.constraints.NotNull;
import org.example.cqrs.command.CommandBuilder;
import org.example.cqrs.command.CommandValidationException;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public class CloseIssueCommand {
    private final IssueId issueId;

    public static CloseIssueCommandBuilder builder() {
        return new CloseIssueCommandBuilder();
    }

    private CloseIssueCommand(IssueId issueId) {
        this.issueId = issueId;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public static class CloseIssueCommandBuilder extends CommandBuilder<CloseIssueCommandBuilder, CloseIssueCommand> {
        @NotNull
        private UUID issueId;

        public CloseIssueCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        /**
         * @throws CommandValidationException see {@linkplain CommandBuilder#build()}
         */
        @Override
        protected CloseIssueCommand create() {
            return new CloseIssueCommand(
                    new IssueId(issueId)
            );
        }
    }
}
