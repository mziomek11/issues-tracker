package com.mateuszziomek.issuestracker.issues.query.readmodel.issue.list;

import com.mateuszziomek.issuestracker.issues.query.domain.Issue;
import com.mateuszziomek.issuestracker.issues.query.domain.Member;
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
