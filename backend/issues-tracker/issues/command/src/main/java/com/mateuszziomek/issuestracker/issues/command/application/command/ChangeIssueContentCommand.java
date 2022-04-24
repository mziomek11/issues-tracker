package com.mateuszziomek.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandBuilder;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueContent;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueId;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ChangeIssueContentCommand {
    private final IssueId issueId;
    private final IssueContent issueContent;
    private final IssueOrganizationDetails organizationDetails;

    public static ChangeIssueContentCommandBuilder builder() {
        return new ChangeIssueContentCommandBuilder();
    }

    public static class ChangeIssueContentCommandBuilder
            extends CommandBuilder<ChangeIssueContentCommandBuilder, ChangeIssueContentCommand> {
        public static final String ISSUE_CONTENT_FIELD_NAME = "issueContent";

        @NotNull
        private UUID issueId;

        @NotBlank
        private String issueContent;

        @NotNull
        private IssueOrganizationDetails organizationDetails;

        public ChangeIssueContentCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public ChangeIssueContentCommandBuilder issueContent(String issueContent) {
            this.issueContent = issueContent;
            return this;
        }

        public ChangeIssueContentCommandBuilder organizationDetails(IssueOrganizationDetails organizationDetails) {
            this.organizationDetails = organizationDetails;
            return this;
        }

        @Override
        protected ChangeIssueContentCommand create() {
            return new ChangeIssueContentCommand(
                    new IssueId(issueId),
                    new IssueContent(issueContent),
                    organizationDetails
            );
        }
    }
}
