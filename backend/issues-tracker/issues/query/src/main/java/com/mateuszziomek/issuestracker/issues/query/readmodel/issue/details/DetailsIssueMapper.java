package com.mateuszziomek.issuestracker.issues.query.readmodel.issue.details;

import com.mateuszziomek.issuestracker.issues.query.domain.Issue;
import com.mateuszziomek.issuestracker.issues.query.domain.IssueUpdate;
import com.mateuszziomek.issuestracker.issues.query.domain.Member;
import com.mateuszziomek.issuestracker.issues.query.domain.Vote;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.DetailsIssue;

import java.util.List;

public class DetailsIssueMapper {

    private DetailsIssueMapper() {}

    public static DetailsIssue fromModel(Issue issue) {
        return DetailsIssue
                .builder()
                .organization(mapOrganization(issue))
                .name(issue.getName())
                .id(issue.getId())
                .creator(mapCreator(issue.getCreator()))
                .project(mapProject(issue))
                .status(issue.getStatus())
                .type(issue.getType())
                .votes(mapVotes(issue.getVotes()))
                .updates(mapUpdates(issue.getUpdates()))
                .build();
    }

    private static DetailsIssue.Organization mapOrganization(Issue issue) {
        return DetailsIssue.Organization
                .builder()
                .id(issue.getOrganizationId())
                .build();
    }

    private static DetailsIssue.Project mapProject(Issue issue) {
        return DetailsIssue.Project
                .builder()
                .id(issue.getProjectId())
                .build();
    }


    private static DetailsIssue.Creator mapCreator(Member creator) {
        return DetailsIssue.Creator
                .builder()
                .id(creator.getId())
                .email(creator.getEmail())
                .build();
    }

    private static List<DetailsIssue.Vote> mapVotes(List<Vote> votes) {
        return votes
                .stream()
                .map(DetailsIssueMapper::mapVote)
                .toList();
    }

    private static DetailsIssue.Vote mapVote(Vote vote) {
        return DetailsIssue.Vote
                .builder()
                .memberId(vote.getMemberId())
                .type(vote.getType())
                .build();
    }

    private static List<DetailsIssue.IssueUpdate> mapUpdates(List<IssueUpdate> updates) {
        return updates
                .stream()
                .map(DetailsIssueMapper::mapUpdate)
                .toList();
    }

    private static DetailsIssue.IssueUpdate mapUpdate(IssueUpdate update) {
        return DetailsIssue.IssueUpdate
                .builder()
                .type(update.getType())
                .updatedAt(update.getUpdatedAt())
                .currentValue(update.getCurrentValue())
                .previousValues(update.getPreviousValues())
                .build();
    }
}
