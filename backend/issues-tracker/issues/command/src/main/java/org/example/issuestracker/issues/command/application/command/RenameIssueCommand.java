package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class RenameIssueCommand {
    private final IssueId issueId;
    private final IssueName issueName;

    public static RenameIssueCommandBuilder builder() {
        return new RenameIssueCommandBuilder();
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

        @Override
        protected RenameIssueCommand create() {
            return new RenameIssueCommand(
                    new IssueId(issueId),
                    new IssueName(issueName)
            );
        }
    }
}
