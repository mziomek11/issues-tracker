package com.mateuszziomek.issuestracker.issues.query.domain.comment;

import com.mateuszziomek.issuestracker.issues.query.domain.Vote;
import com.mateuszziomek.issuestracker.issues.query.domain.member.Member;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueCommentContentChangedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueCommentedEvent;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.CommentStatus;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.CommentUpdateType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private UUID id;
    private String content;
    private CommentStatus status;
    private List<Vote> votes;
    private List<CommentUpdate> updates;
    private Member creator;

    public static Comment create(IssueCommentedEvent event, Member creator) {
        var comment = new Comment();
        comment.id = event.getCommentId();
        comment.content = event.getCommentContent();
        comment.status = CommentStatus.ACTIVE;
        comment.votes = new ArrayList<>();
        comment.updates = new ArrayList<>();
        comment.creator = creator;
        return comment;
    }

    public void changeContent(IssueCommentContentChangedEvent event) {
        var update = new CommentUpdate(
                CommentUpdateType.CONTENT_CHANGED,
                event.getDate(),
                content,
                event.getCommentContent()
        );

        updates.add(update);
        content = event.getCommentContent();
    }

    public void hide() {
        status = CommentStatus.HIDDEN;
    }

    public void vote(Vote vote) {
        votes.removeIf(v -> v.getMemberId().equals(vote.getMemberId()));
        votes.add(vote);
    }
}
