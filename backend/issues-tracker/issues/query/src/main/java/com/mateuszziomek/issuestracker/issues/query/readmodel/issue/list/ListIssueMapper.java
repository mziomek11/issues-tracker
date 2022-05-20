package com.mateuszziomek.issuestracker.issues.query.readmodel.issue.list;

import com.mateuszziomek.issuestracker.issues.query.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.query.domain.member.Member;
import com.mateuszziomek.issuestracker.issues.query.domain.Vote;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.ListIssue;

import java.util.List;

public class ListIssueMapper {
    private ListIssueMapper() {}

    public static ListIssue fromModel(Issue issue) {
        return ListIssue
                .builder()
                .id(issue.getId())
                .name(issue.getName())
                .creator(mapCreator(issue.getCreator()))
                .type(issue.getType())
                .status(issue.getStatus())
                .project(mapProject(issue))
                .organization(mapOrganization(issue))
                .votes(mapVotes(issue.getVotes()))
                .build();
    }

    private static ListIssue.Creator mapCreator(Member member) {
        return ListIssue.Creator
                .builder()
                .id(member.getId())
                .email(member.getEmail())
                .build();
    }

    private static ListIssue.Project mapProject(Issue issue) {
        return ListIssue.Project
                .builder()
                .id(issue.getProjectId())
                .build();
    }

    private static ListIssue.Organization mapOrganization(Issue issue) {
        return ListIssue.Organization
                .builder()
                .id(issue.getOrganizationId())
                .build();
    }

    private static List<ListIssue.Vote> mapVotes(List<Vote> votes) {
        return votes.stream().map(ListIssueMapper::mapVote).toList();
    }

    private static ListIssue.Vote mapVote(Vote vote) {
        return ListIssue.Vote
                .builder()
                .memberId(vote.getMemberId())
                .type(vote.getType())
                .build();
    }
}
