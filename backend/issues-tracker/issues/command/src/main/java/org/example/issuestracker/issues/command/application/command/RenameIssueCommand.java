package org.example.issuestracker.issues.command.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.cqrs.command.CommandBuilder;
import org.example.cqrs.command.CommandValidationException;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;

import java.util.UUID;

public class RenameIssueCommand {
    private final IssueId issueId;
    private final IssueName issueName;

    public static RenameIssueCommandBuilder builder() {
        return new RenameIssueCommandBuilder();
    }

    private RenameIssueCommand(IssueId issueId, IssueName issueName) {
        this.issueId = issueId;
        this.issueName = issueName;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public IssueName getIssueName() {
        return issueName;
    }

    public static class RenameIssueCommandBuilder extends CommandBuilder<RenameIssueCommandBuilder, RenameIssueCommand> {
        @NotNull
        private UUID issueId;

        @NotBlank
        private String issueName;

        public RenameIssueCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public RenameIssueCommandBuilder issueName(String issueName) {
            this.issueName = issueName;
            return this;
        }

        /**
         * @throws CommandValidationException see {@linkplain CommandBuilder#build()}
         */
        @Override
        protected RenameIssueCommand create() {
            return new RenameIssueCommand(
                    new IssueId(issueId),
                    new IssueName(issueName)
            );
        }
    }
}
