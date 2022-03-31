package org.example.issuetracker.issues.domain.issue;

import static org.assertj.core.api.Assertions.*;

import org.example.issuestracker.issues.command.domain.comment.Comment;
import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentWithIdExistsException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;
import org.example.issuestracker.issues.command.domain.issue.exception.*;
import org.example.issuestracker.issues.command.domain.vote.Vote;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import org.example.issuestracker.issues.common.domain.issue.IssueType;
import org.example.issuestracker.issues.common.domain.vote.VoteType;
import org.example.issuestracker.issues.common.event.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;

class IssueTest {
    private final UUID ISSUE_UUID = UUID.randomUUID();
    private final String ISSUE_ID = ISSUE_UUID.toString();
    private final String ISSUE_NAME = "Example name";
    private final String ISSUE_CONTENT = "Example content";
    private final IssueType ISSUE_TYPE = IssueType.BUG;

    private final UUID FIRST_COMMENT_UUID = UUID.randomUUID();
    private final String FIRST_COMMENT_ID = FIRST_COMMENT_UUID.toString();
    private final String FIRST_COMMENT_CONTENT = "Example first comment content";
    private final UUID SECOND_COMMENT_UUID = UUID.randomUUID();
    private final String SECOND_COMMENT_CONTENT = "Example second comment content";

    private final VoterId FIRST_VOTER_ID = new VoterId(UUID.randomUUID());
    private final VoterId SECOND_VOTER_ID = new VoterId(UUID.randomUUID());

    @Test
    void openingIssueRaisesIssueOpenedEvent() {
        // Arrange
        var randomUUID = UUID.randomUUID();
        var id = new IssueId(randomUUID);
        var content = new IssueContent("Example content");
        var name = new IssueName("Example name");
        var sut = Issue.open(id, IssueType.BUG, content, name);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(1);

        var event = sut.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(IssueOpenedEvent.class);

        var issueOpenedEvent = (IssueOpenedEvent) event;
        assertThat(issueOpenedEvent.getIssueContent()).isEqualTo("Example content");
        assertThat(issueOpenedEvent.getIssueType()).isEqualTo(IssueType.BUG);
        assertThat(issueOpenedEvent.getId()).isEqualTo(randomUUID.toString());
        assertThat(issueOpenedEvent.getIssueName()).isEqualTo("Example name");
    }

    @Test
    void closingIssueRaisesIssueClosedEvent() {
        // Arrange
        var sut = openIssue();

        // Act
        sut.close();

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(1);

        var event = sut.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(IssueClosedEvent.class);

        var issueCloseEvent = (IssueClosedEvent) event;
        assertThat(issueCloseEvent.getId()).isEqualTo(ISSUE_ID);
    }

    @Test
    void issueCanBeClosedOnlyOnce() {
        // Arrange
        var sut = openIssue();
        sut.close();
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(IssueClosedException.class).isThrownBy(() -> sut.close());
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void renamingIssueRaisesIssueRenamedEvent() {
        // Arrange
        var name = new IssueName("Another name");
        var sut = openIssue();

        // Act
        sut.rename(name);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(1);

        var event = sut.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(IssueRenamedEvent.class);

        var issueRenamedEvent = (IssueRenamedEvent) event;
        assertThat(issueRenamedEvent.getId()).isEqualTo(ISSUE_ID);
        assertThat(issueRenamedEvent.getIssueName()).isEqualTo("Another name");
    }

    @Test
    void closedIssueCanNotBeRenamed() {
        // Arrange
        var name = new IssueName("Another name");
        var sut = openIssue();
        sut.close();
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(IssueClosedException.class).isThrownBy(() -> sut.rename(name));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void issueCanNotBeRenamedToTheSameName() {
        // Arrange
        var newName = new IssueName(ISSUE_NAME);
        var sut = openIssue();

        // Assert
        assertThatExceptionOfType(IssueNameSetException.class).isThrownBy(() -> sut.rename(newName));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void changingIssueTypeRaisesIssueTypeChangeEvent() {
        // Arrange
        var sut = openIssue();

        // Act
        sut.changeType(IssueType.ENHANCEMENT);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(1);

        var event = sut.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(IssueTypeChangedEvent.class);

        var issueTypeChangedEvent = (IssueTypeChangedEvent) event;
        assertThat(issueTypeChangedEvent.getId()).isEqualTo(ISSUE_ID);
        assertThat(issueTypeChangedEvent.getIssueType()).isEqualTo(IssueType.ENHANCEMENT);
    }

    @Test
    void typeOfClosedIssueCanNotBeChanged() {
        // Arrange
        var sut = openIssue();
        sut.close();
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(IssueClosedException.class).isThrownBy(() -> sut.changeType(IssueType.ENHANCEMENT));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void typeOfIssueCanNotBeChangedToTheSameType() {
        // Arrange
        var sut = openIssue();

        // Assert
        assertThatExceptionOfType(IssueTypeSetException.class).isThrownBy(() -> sut.changeType(ISSUE_TYPE));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void changingIssueContentRaisesIssueContentChangedEvent() {
        // Arrange
        var content = new IssueContent("Another content");
        var sut = openIssue();

        // Act
        sut.changeContent(content);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(1);

        var event = sut.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(IssueContentChangedEvent.class);

        var issueContentChangedEvent = (IssueContentChangedEvent) event;
        assertThat(issueContentChangedEvent.getId()).isEqualTo(ISSUE_ID);
        assertThat(issueContentChangedEvent.getIssueContent()).isEqualTo("Another content");
    }

    @Test
    void contentOfClosedIssueCanNotBeChanged() {
        // Arrange
        var content = new IssueContent("Another content");
        var sut = openIssue();
        sut.close();
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(IssueClosedException.class).isThrownBy(() -> sut.changeContent(content));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void contentOfIssueCanNotBeChangedToTheSameContent() {
        // Arrange
        var content = new IssueContent(ISSUE_CONTENT);
        var sut = openIssue();

        // Assert
        assertThatExceptionOfType(IssueContentSetException.class).isThrownBy(() -> sut.changeContent(content));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void commentingIssueRaisesIssueCommentedEvent() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();

        // Act
        sut.comment(firstComment);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(1);

        var event = sut.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(IssueCommentedEvent.class);

        var issueCommentedEvent = (IssueCommentedEvent) event;
        assertThat(issueCommentedEvent.getId()).isEqualTo(ISSUE_ID);
        assertThat(issueCommentedEvent.getCommentContent()).isEqualTo(FIRST_COMMENT_CONTENT);
        assertThat(issueCommentedEvent.getCommentId()).isEqualTo(FIRST_COMMENT_ID);
    }

    @Test
    void issueCanHaveManyComments() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();
        var secondComment = createSecondComment();

        // Act
        sut.comment(firstComment);
        sut.comment(secondComment);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(2);
    }

    @Test
    void closedIssueCanNotBeCommented() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();
        sut.close();
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(IssueClosedException.class).isThrownBy(() -> sut.comment(firstComment));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void issueCanNotHaveTwoCommentsWithTheSameId() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();
        sut.comment(firstComment);
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(CommentWithIdExistsException.class).isThrownBy(() -> sut.comment(firstComment));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void changingIssueCommentContentRaisesChangedIssueCommentContentEvent() {
        // Arrange
        var content = new CommentContent("Another content");
        var sut = openIssue();
        var firstComment = createFirstComment();
        sut.comment(firstComment);
        sut.markChangesAsCommitted();

        // Act
        sut.changeCommentContent(firstComment.getId(), content);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(1);

        var event = sut.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(IssueCommentContentChangedEvent.class);

        var issueCommentContentChangedEvent = (IssueCommentContentChangedEvent) event;
        assertThat(issueCommentContentChangedEvent.getId()).isEqualTo(ISSUE_ID);
        assertThat(issueCommentContentChangedEvent.getCommentId()).isEqualTo(FIRST_COMMENT_ID);
        assertThat(issueCommentContentChangedEvent.getCommentContent()).isEqualTo("Another content");
    }

    @Test
    void contentOfCommentCanNotBeChangedWhenIssueIsClosed() {
        // Arrange
        var content = new CommentContent("Another content");
        var sut = openIssue();
        var firstComment = createFirstComment();
        sut.comment(firstComment);
        sut.close();
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(IssueClosedException.class)
                .isThrownBy(() -> sut.changeCommentContent(firstComment.getId(), content));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void contentOfCommentCanNotBeChangedToTheSameContent() {
        // Arrange
        var content = new CommentContent(FIRST_COMMENT_CONTENT);
        var sut = openIssue();
        var firstComment = createFirstComment();
        sut.comment(firstComment);
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(CommentContentSetException.class)
                .isThrownBy(() -> sut.changeCommentContent(firstComment.getId(), content));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void contentOfNotExistingCommentCanNotBeChanged() {
        // Arrange
        var content = new CommentContent("Another content");
        var sut = openIssue();
        var firstComment = createFirstComment();
        var secondCommentId = createSecondComment().getId();
        sut.comment(firstComment);
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(CommentNotFoundException.class)
                .isThrownBy(() -> sut.changeCommentContent(secondCommentId, content));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void hidingCommentRaisesIssueCommentHiddenEvent() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();
        sut.comment(firstComment);
        sut.markChangesAsCommitted();

        // Act
        sut.hideComment(firstComment.getId());

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(1);

        var event = sut.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(IssueCommentHiddenEvent.class);

        var issueCommentHiddenEvent = (IssueCommentHiddenEvent) event;
        assertThat(issueCommentHiddenEvent.getId()).isEqualTo(ISSUE_ID);
        assertThat(issueCommentHiddenEvent.getCommentId()).isEqualTo(FIRST_COMMENT_ID);
    }

    @Test
    void commentContentCanNotBeHiddenWhenIssueIsClosed() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();
        sut.comment(firstComment);
        sut.close();
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(IssueClosedException.class)
                .isThrownBy(() -> sut.hideComment(firstComment.getId()));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void notExistingCommentCanNotBeHidden() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();

        // Assert
        assertThatExceptionOfType(CommentNotFoundException.class)
                .isThrownBy(() -> sut.hideComment(firstComment.getId()));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void hiddenCommentCanNotBeHiddenAgain() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();
        sut.comment(firstComment);
        sut.hideComment(firstComment.getId());
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(CommentHiddenException.class)
                .isThrownBy(() -> sut.hideComment(firstComment.getId()));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @ParameterizedTest
    @EnumSource(VoteType.class)
    void votingCommentRaisesIssueCommentVotedEvent(VoteType voteType) {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();
        var vote = new Vote(FIRST_VOTER_ID, voteType);
        sut.comment(firstComment);
        sut.markChangesAsCommitted();

        // Act
        sut.voteComment(firstComment.getId(), vote);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(1);

        var event = sut.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(IssueCommentVotedEvent.class);

        var issueCommentVotedEvent = (IssueCommentVotedEvent) event;
        assertThat(issueCommentVotedEvent.getId()).isEqualTo(ISSUE_ID);
        assertThat(issueCommentVotedEvent.getCommentId()).isEqualTo(FIRST_COMMENT_ID);
        assertThat(issueCommentVotedEvent.getVoterId()).isEqualTo(FIRST_VOTER_ID.toString());
        assertThat(issueCommentVotedEvent.getVoteType()).isEqualTo(voteType);
    }

    @Test
    void commentCanNotBeVotedWhenIssueIsClosed() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        sut.comment(firstComment);
        sut.close();
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(IssueClosedException.class)
                .isThrownBy(() -> sut.voteComment(firstComment.getId(), vote));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void notExistingCommentCanNotBeVoted() {
        // Arrange
        var sut = openIssue();
        var commentId = new CommentId(FIRST_COMMENT_UUID);
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);

        // Assert
        assertThatExceptionOfType(CommentNotFoundException.class)
                .isThrownBy(() -> sut.voteComment(commentId, vote));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void voterCanVoteCommentWithDifferentVoteTypes() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        var anotherVote = new Vote(FIRST_VOTER_ID, VoteType.DOWN);
        sut.comment(firstComment);
        sut.markChangesAsCommitted();

        // Act
        sut.voteComment(firstComment.getId(), vote);
        sut.voteComment(firstComment.getId(), anotherVote);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(2);
    }

    @Test
    void voterCanNotVoteCommentWithTheSameVoteType() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        sut.comment(firstComment);
        sut.voteComment(firstComment.getId(), vote);
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(VoteAlreadyExistsException.class)
                .isThrownBy(() -> sut.voteComment(firstComment.getId(), vote));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void commentCanHaveManyVotes() {
        // Arrange
        var sut = openIssue();
        var firstComment = createFirstComment();
        var firstVote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        var secondVote = new Vote(SECOND_VOTER_ID, VoteType.UP);
        sut.comment(firstComment);
        sut.markChangesAsCommitted();

        // Act
        sut.voteComment(firstComment.getId(), firstVote);
        sut.voteComment(firstComment.getId(), secondVote);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(2);
    }

    @Test
    void votingIssueRaisesIssueVotedEvent() {
        // Arrange
        var sut = openIssue();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);

        // Act
        sut.vote(vote);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(1);

        var event = sut.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(IssueVotedEvent.class);

        var issueVotedEvent = (IssueVotedEvent) event;
        assertThat(issueVotedEvent.getId()).isEqualTo(ISSUE_ID);
        assertThat(issueVotedEvent.getVoterId()).isEqualTo(FIRST_VOTER_ID.toString());
        assertThat(issueVotedEvent.getVoteType()).isEqualTo(VoteType.UP);
    }

    @Test
    void closedIssueCanNotBeVoted() {
        // Arrange
        var sut = openIssue();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        sut.close();
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(IssueClosedException.class)
                .isThrownBy(() -> sut.vote(vote));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void voterCanVoteIssueWithDifferentVoteTypes() {
        // Arrange
        var sut = openIssue();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        var anotherVote = new Vote(FIRST_VOTER_ID, VoteType.DOWN);

        // Act
        sut.vote(vote);
        sut.vote(anotherVote);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(2);
    }

    @Test
    void voterCanNotVoteIssueWithTheSameVotesTypes() {
        // Arrange
        var sut = openIssue();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        sut.vote(vote);
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(VoteAlreadyExistsException.class)
                .isThrownBy(() -> sut.vote(vote));
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    private Issue openIssue() {
        var id = new IssueId(ISSUE_UUID);
        var content = new IssueContent(ISSUE_CONTENT);
        var name = new IssueName(ISSUE_NAME);
        var issue = Issue.open(id, ISSUE_TYPE, content, name);

        issue.markChangesAsCommitted();

        return issue;
    }

    private Comment createFirstComment() {
        var id = new CommentId(FIRST_COMMENT_UUID);
        var content = new CommentContent(FIRST_COMMENT_CONTENT);

        return new Comment(id, content);
    }

    private Comment createSecondComment() {
        var id = new CommentId(SECOND_COMMENT_UUID);
        var content = new CommentContent(SECOND_COMMENT_CONTENT);

        return new Comment(id, content);
    }

}
