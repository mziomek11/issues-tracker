package com.mateuszziomek.issuestracker.issues.query.domain;

import com.mateuszziomek.issuestracker.shared.domain.event.*;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueStatus;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Issue {
    private UUID id;
    private UUID projectId;
    private UUID organizationId;
    private String name;
    private String content;
    private IssueStatus status;
    private IssueType type;
    private List<Vote> votes;
    private Member creator;

    public static Issue create(IssueOpenedEvent event, Member creator) {
        var issue = new Issue();
        issue.id = event.getId();
        issue.projectId = event.getProjectId();
        issue.organizationId = event.getOrganizationId();
        issue.content = event.getIssueContent();
        issue.name = event.getIssueName();
        issue.status = IssueStatus.OPENED;
        issue.type = event.getIssueType();
        issue.votes = new ArrayList<>();
        issue.creator = creator;
        return issue;
    }

    public void rename(IssueRenamedEvent event) {
        name = event.getIssueName();
    }

    public void close() {
        status = IssueStatus.CLOSED;
    }

    public void changeContent(IssueContentChangedEvent event) {
        content = event.getIssueContent();
    }

    public void changeType(IssueTypeChangedEvent event) {
        type = event.getIssueType();
    }

    public void vote(IssueVotedEvent event) {
        var newVotes = votes
                .stream()
                .filter(vote -> vote.getMemberId() != event.getMemberId())
                .toList();

        var vote = new Vote(event.getMemberId(), event.getVoteType());
        newVotes.add(vote);

        votes = newVotes;
    }
}
