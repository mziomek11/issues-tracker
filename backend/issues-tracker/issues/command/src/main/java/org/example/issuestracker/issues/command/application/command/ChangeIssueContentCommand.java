package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ChangeIssueContentCommand {
    private final IssueId issueId;
    private final IssueContent issueContent;

    public static ChangeIssueContentCommandBuilder builder() {
        return new ChangeIssueContentCommandBuilder();
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
