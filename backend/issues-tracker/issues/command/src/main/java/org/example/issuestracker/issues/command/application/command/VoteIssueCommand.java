package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import org.example.issuestracker.shared.domain.valueobject.VoteType;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class VoteIssueCommand {
    private final IssueId issueId;
    private final VoteType voteType;
    private final IssueOrganizationDetails organizationDetails;


    public static VoteIssueCommandBuilder builder() {
        return new VoteIssueCommandBuilder();
    }

    public static class VoteIssueCommandBuilder extends CommandBuilder<VoteIssueCommandBuilder, VoteIssueCommand> {
        public static final String VOTE_TYPE_FIELD_NAME = "voteType";

        @NotNull
        private UUID issueId;

        @NotNull
        private VoteType voteType;

        @NotNull
        private IssueOrganizationDetails organizationDetails;

        public VoteIssueCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public VoteIssueCommandBuilder voteType(VoteType voteType) {
            this.voteType = voteType;
            return this;
        }

        public VoteIssueCommandBuilder organizationDetails(IssueOrganizationDetails organizationDetails) {
            this.organizationDetails = organizationDetails;
            return this;
        }

        @Override
        protected VoteIssueCommand create() {
            return new VoteIssueCommand(
                    new IssueId(issueId),
                    voteType,
                    organizationDetails
            );
        }
    }
}
