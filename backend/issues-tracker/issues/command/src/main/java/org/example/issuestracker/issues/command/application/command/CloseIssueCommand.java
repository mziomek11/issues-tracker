package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CloseIssueCommand {
    private final IssueId issueId;

    public static CloseIssueCommandBuilder builder() {
        return new CloseIssueCommandBuilder();
    }

    public static class CloseIssueCommandBuilder extends CommandBuilder<CloseIssueCommandBuilder, CloseIssueCommand> {
        @NotNull
        private UUID issueId;

        public CloseIssueCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        @Override
        protected CloseIssueCommand create() {
            return new CloseIssueCommand(
                    new IssueId(issueId)
            );
        }
    }
}
