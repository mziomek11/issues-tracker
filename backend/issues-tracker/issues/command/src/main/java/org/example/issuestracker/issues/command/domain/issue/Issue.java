package org.example.issuestracker.issues.command.domain.issue;

import org.example.cqrs.domain.AggregateRoot;
import org.example.issuestracker.issues.command.domain.comment.Comment;
import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueContentAlreadySetException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNameAlreadySetException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueTypeAlreadySetException;
import org.example.issuestracker.issues.common.domain.IssueStatus;
import org.example.issuestracker.issues.common.domain.IssueType;
import org.example.issuestracker.issues.common.event.*;

import java.util.ArrayList;
import java.util.List;

import static org.example.issuestracker.issues.command.domain.EventFactory.*;

public class Issue extends AggregateRoot {
    private IssueId id;
    private IssueType type;
    private IssueStatus status;
    private IssueContent content;
    private IssueName name;
    private List<Comment> comments = new ArrayList<>();

    public static Issue open(IssueId id, IssueType type, IssueContent content, IssueName name) {
        var issue = new Issue();

        issue.raiseEvent(issueOpened(id, type, content, name));

        return issue;
    }

    public Issue() {}

    public void close() {
        ensureIsOpen();
        raiseEvent(issueClosed(id));
    }

    public void rename(IssueName newName) {
        ensureIsOpen();

        if (name.equals(newName)) {
            throw new IssueNameAlreadySetException();
        }

        raiseEvent(issueRenamed(id, newName));
    }

    public void changeType(IssueType newType) {
        ensureIsOpen();

        if (type.equals(newType)) {
            throw new IssueTypeAlreadySetException();
        }

        raiseEvent(issueTypeChanged(id, newType));
    }

    public void changeContent(IssueContent newContent) {
        ensureIsOpen();

        if (content.equals(newContent)) {
            throw new IssueContentAlreadySetException();
        }

        raiseEvent(issueContentChanged(id, newContent));
    }

    public void comment(Comment comment) {
        ensureIsOpen();
        raiseEvent(issueCommented(id, comment.getId(), comment.getContent()));
    }

    public void on(IssueOpenedEvent issueOpenedEvent) {
        this.id = IssueId.fromString(issueOpenedEvent.getId());
        this.type = issueOpenedEvent.getIssueType();
        this.status = IssueStatus.OPENED;
        this.content = new IssueContent(issueOpenedEvent.getIssueContent());
        this.name = new IssueName(issueOpenedEvent.getIssueName());
    }

    public void on(IssueClosedEvent issueClosedEvent) {
        this.status = IssueStatus.CLOSED;
    }

    public void on(IssueRenamedEvent issueRenamedEvent) {
        this.name = new IssueName(issueRenamedEvent.getIssueName());
    }

    public void on(IssueTypeChangedEvent issueTypeChangedEvent) {
        this.type = issueTypeChangedEvent.getIssueType();
    }

    public void on(IssueContentChangedEvent issueContentChangedEvent) {
        this.content = new IssueContent(issueContentChangedEvent.getIssueContent());
    }

    public void on(IssueCommentedEvent issueCommentedEvent) {
        var commentId = CommentId.fromString(issueCommentedEvent.getCommentId());
        var commentContent = new CommentContent(issueCommentedEvent.getCommentContent());
        var comment = new Comment(commentId, commentContent);

        comments.add(comment);
    }

    @Override
    public IssueId getId() {
        return id;
    }

    private void ensureIsOpen() {
        if (isClosed()) {
            throw new IssueClosedException();
        }
    }

    private boolean isClosed() {
        return status.equals(IssueStatus.CLOSED);
    }
}
