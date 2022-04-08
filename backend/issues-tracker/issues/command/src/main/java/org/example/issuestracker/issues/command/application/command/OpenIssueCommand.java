package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;
import org.example.issuestracker.issues.common.domain.issue.IssueType;

import java.util.UUID;

public class OpenIssueCommand {
    private final IssueId issueId;
    private final IssueType issueType;
    private final IssueContent issueContent;
    private final IssueName issueName;

    public static OpenIssueCommandBuilder builder() {
        return new OpenIssueCommandBuilder();
    }

    private OpenIssueCommand(IssueId issueId, IssueType issueType, IssueContent issueContent, IssueName issueName) {
        this.issueId = issueId;
        this.issueType = issueType;
        this.issueContent = issueContent;
        this.issueName = issueName;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public IssueContent getIssueContent() {
        return issueContent;
    }

    public IssueName getIssueName() {
        return issueName;
    }

    public static class OpenIssueCommandBuilder extends CommandBuilder<OpenIssueCommandBuilder, OpenIssueCommand> {
        @NotNull
        private UUID issueId;

        @NotNull
        private IssueType issueType;

        @NotBlank
        private String issueContent;

        @NotBlank
        private String issueName;

        public OpenIssueCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public OpenIssueCommandBuilder issueType(IssueType issueType) {
            this.issueType = issueType;
            return this;
        }

        public OpenIssueCommandBuilder issueContent(String issueContent) {
            this.issueContent = issueContent;
            return this;
        }

        public OpenIssueCommandBuilder issueName(String issueName) {
            this.issueName = issueName;
            return this;
        }

        @Override
        protected OpenIssueCommand create() {
            return new OpenIssueCommand(
                    new IssueId(issueId),
                    issueType,
                    new IssueContent(issueContent),
                    new IssueName(issueName)
            );
        }
    }
}
