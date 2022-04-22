package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CloseIssueCommand {
    private final IssueId issueId;
    private final IssueOrganizationDetails organizationDetails;

    public static CloseIssueCommandBuilder builder() {
        return new CloseIssueCommandBuilder();
    }

    public static class CloseIssueCommandBuilder extends CommandBuilder<CloseIssueCommandBuilder, CloseIssueCommand> {
        @NotNull
        private UUID issueId;

        @NotNull
        private IssueOrganizationDetails organizationDetails;

        public CloseIssueCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public CloseIssueCommandBuilder organizationDetails(IssueOrganizationDetails organizationDetails) {
            this.organizationDetails = organizationDetails;
            return this;
        }

        @Override
        protected CloseIssueCommand create() {
            return new CloseIssueCommand(
                    new IssueId(issueId),
                    organizationDetails
            );
        }
    }
}
