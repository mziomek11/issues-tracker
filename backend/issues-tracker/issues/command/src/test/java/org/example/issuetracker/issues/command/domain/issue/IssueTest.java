package org.example.issuetracker.issues.command.domain.issue;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.ThrowableAssert;
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
    private final String ISSUE_ID_PLAIN = ISSUE_UUID.toString();
    private final String ISSUE_NAME_PLAIN = "Example name";
    private final String ISSUE_CONTENT_PLAIN = "Example content";
    private final IssueType ISSUE_TYPE = IssueType.BUG;

    private final UUID FIRST_COMMENT_UUID = UUID.randomUUID();
    private final CommentId FIRST_COMMENT_ID = new CommentId(FIRST_COMMENT_UUID);
    private final String FIRST_COMMENT_ID_PLAIN = FIRST_COMMENT_UUID.toString();
    private final String FIRST_COMMENT_CONTENT_PLAIN = "Example first comment content";
    private final UUID SECOND_COMMENT_UUID = UUID.randomUUID();
    private final CommentId SECOND_COMMENT_ID = new CommentId(SECOND_COMMENT_UUID);


    private final UUID FIRST_VOTER_UUID = UUID.randomUUID();
    private final VoterId FIRST_VOTER_ID = new VoterId(FIRST_VOTER_UUID);
    private final String FIRST_VOTER_ID_PLAIN = FIRST_VOTER_UUID.toString();
    private final VoterId SECOND_VOTER_ID = new VoterId(UUID.randomUUID());

    @Test
    void openingIssueRaisesIssueOpenedEvent() {
        // Arrange
        var randomUUID = UUID.randomUUID();
        var id = new IssueId(randomUUID);
        var content = new IssueContent("Example content");
        var name = new IssueName("Example name");

        // Act
        var sut = Issue.open(id, IssueType.BUG, content, name);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, IssueOpenedEvent.class);

        var issueOpenedEvent = (IssueOpenedEvent) sut.getUncommittedChanges().get(0);
        assertThat(issueOpenedEvent.getIssueContent()).isEqualTo("Example content");
        assertThat(issueOpenedEvent.getIssueType()).isEqualTo(IssueType.BUG);
        assertThat(issueOpenedEvent.getId()).isEqualTo(randomUUID.toString());
        assertThat(issueOpenedEvent.getIssueName()).isEqualTo("Example name");
    }

    @Test
    void closingIssueRaisesIssueClosedEvent() {
        // Arrange
        var sut = createIssue();

        // Act
        sut.close();

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, IssueClosedEvent.class);

        var issueCloseEvent = (IssueClosedEvent) sut.getUncommittedChanges().get(0);
        assertThat(issueCloseEvent.getId()).isEqualTo(ISSUE_ID_PLAIN);
    }

    @Test
    void issueCanBeClosedOnlyOnce() {
        // Arrange
        var sut = createClosedIssue();

        // Assert
        assertThatIssueClosedExceptionIsThrownBy(sut::close);
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void renamingIssueRaisesIssueRenamedEvent() {
        // Arrange
        var sut = createIssue();
        var name = new IssueName("Another name");

        // Act
        sut.rename(name);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, IssueRenamedEvent.class);

        var issueRenamedEvent = (IssueRenamedEvent) sut.getUncommittedChanges().get(0);
        assertThat(issueRenamedEvent.getId()).isEqualTo(ISSUE_ID_PLAIN);
        assertThat(issueRenamedEvent.getIssueName()).isEqualTo("Another name");
    }

    @Test
    void closedIssueCanNotBeRenamed() {
        // Arrange
        var sut = createClosedIssue();
        var name = new IssueName("Another name");

        // Assert
        assertThatIssueClosedExceptionIsThrownBy(() -> sut.rename(name));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void issueCanNotBeRenamedToTheSameName() {
        // Arrange
        var sut = createIssue();
        var newName = new IssueName(ISSUE_NAME_PLAIN);

        // Assert
        assertThatExceptionOfType(IssueNameSetException.class).isThrownBy(() -> sut.rename(newName));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void changingIssueTypeRaisesIssueTypeChangeEvent() {
        // Arrange
        var sut = createIssue();

        // Act
        sut.changeType(IssueType.ENHANCEMENT);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, IssueTypeChangedEvent.class);

        var issueTypeChangedEvent = (IssueTypeChangedEvent) sut.getUncommittedChanges().get(0);
        assertThat(issueTypeChangedEvent.getId()).isEqualTo(ISSUE_ID_PLAIN);
        assertThat(issueTypeChangedEvent.getIssueType()).isEqualTo(IssueType.ENHANCEMENT);
    }

    @Test
    void typeOfClosedIssueCanNotBeChanged() {
        // Arrange
        var sut = createClosedIssue();

        // Assert
        assertThatIssueClosedExceptionIsThrownBy(() -> sut.changeType(IssueType.ENHANCEMENT));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void typeOfIssueCanNotBeChangedToTheSameType() {
        // Arrange
        var sut = createIssue();

        // Assert
        assertThatExceptionOfType(IssueTypeSetException.class).isThrownBy(() -> sut.changeType(ISSUE_TYPE));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void changingIssueContentRaisesIssueContentChangedEvent() {
        // Arrange
        var sut = createIssue();
        var content = new IssueContent("Another content");

        // Act
        sut.changeContent(content);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, IssueContentChangedEvent.class);

        var issueContentChangedEvent = (IssueContentChangedEvent) sut.getUncommittedChanges().get(0);
        assertThat(issueContentChangedEvent.getId()).isEqualTo(ISSUE_ID_PLAIN);
        assertThat(issueContentChangedEvent.getIssueContent()).isEqualTo("Another content");
    }

    @Test
    void contentOfClosedIssueCanNotBeChanged() {
        // Arrange
        var sut = createClosedIssue();
        var content = new IssueContent("Another content");

        // Assert
        assertThatIssueClosedExceptionIsThrownBy(() -> sut.changeContent(content));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void contentOfIssueCanNotBeChangedToTheSameContent() {
        // Arrange
        var sut = createIssue();
        var content = new IssueContent(ISSUE_CONTENT_PLAIN);

        // Assert
        assertThatExceptionOfType(IssueContentSetException.class).isThrownBy(() -> sut.changeContent(content));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void commentingIssueRaisesIssueCommentedEvent() {
        // Arrange
        var sut = createIssue();
        var firstComment = createFirstComment();

        // Act
        sut.comment(firstComment);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, IssueCommentedEvent.class);

        var issueCommentedEvent = (IssueCommentedEvent) sut.getUncommittedChanges().get(0);
        assertThat(issueCommentedEvent.getId()).isEqualTo(ISSUE_ID_PLAIN);
        assertThat(issueCommentedEvent.getCommentContent()).isEqualTo(FIRST_COMMENT_CONTENT_PLAIN);
        assertThat(issueCommentedEvent.getCommentId()).isEqualTo(FIRST_COMMENT_ID_PLAIN);
    }

    @Test
    void issueCanHaveManyComments() {
        // Arrange
        var sut = createIssue();
        var firstComment = createFirstComment();
        var secondComment = createSecondComment();

        // Act
        sut.comment(firstComment);
        sut.comment(secondComment);

        // Assert
        assertThatAmountOfRaisedEventsIsEqualTo(sut, 2);
    }

    @Test
    void closedIssueCanNotBeCommented() {
        // Arrange
        var sut = createClosedIssue();
        var firstComment = createFirstComment();

        // Assert
        assertThatIssueClosedExceptionIsThrownBy(() -> sut.comment(firstComment));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void issueCanNotHaveTwoCommentsWithTheSameId() {
        // Arrange
        var sut = createIssueWithFirstComment();
        var firstComment = createFirstComment();

        // Assert
        assertThatExceptionOfType(CommentWithIdExistsException.class).isThrownBy(() -> sut.comment(firstComment));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void changingIssueCommentContentRaisesChangedIssueCommentContentEvent() {
        // Arrange
        var sut = createIssueWithFirstComment();
        var content = new CommentContent("Another content");

        // Act
        sut.changeCommentContent(FIRST_COMMENT_ID, content);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, IssueCommentContentChangedEvent.class);

        var issueCommentContentChangedEvent = (IssueCommentContentChangedEvent) sut.getUncommittedChanges().get(0);
        assertThat(issueCommentContentChangedEvent.getId()).isEqualTo(ISSUE_ID_PLAIN);
        assertThat(issueCommentContentChangedEvent.getCommentId()).isEqualTo(FIRST_COMMENT_ID_PLAIN);
        assertThat(issueCommentContentChangedEvent.getCommentContent()).isEqualTo("Another content");
    }

    @Test
    void contentOfCommentCanNotBeChangedWhenIssueIsClosed() {
        // Arrange
        var sut = createClosedIssueWithFirstComment();
        var content = new CommentContent("Another content");

        // Assert
        assertThatIssueClosedExceptionIsThrownBy(() -> sut.changeCommentContent(FIRST_COMMENT_ID, content));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void contentOfCommentCanNotBeChangedToTheSameContent() {
        // Arrange
        var sut = createIssueWithFirstComment();
        var content = new CommentContent(FIRST_COMMENT_CONTENT_PLAIN);

        // Assert
        assertThatExceptionOfType(CommentContentSetException.class)
                .isThrownBy(() -> sut.changeCommentContent(FIRST_COMMENT_ID, content));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void contentOfNotExistingCommentCanNotBeChanged() {
        // Arrange
        var sut = createIssueWithFirstComment();
        var content = new CommentContent("Another content");

        // Assert
        assertThatCommentNotFoundExceptionIsThrownBy(() -> sut.changeCommentContent(SECOND_COMMENT_ID, content));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void hidingCommentRaisesIssueCommentHiddenEvent() {
        // Arrange
        var sut = createIssueWithFirstComment();

        // Act
        sut.hideComment(FIRST_COMMENT_ID);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, IssueCommentHiddenEvent.class);

        var issueCommentHiddenEvent = (IssueCommentHiddenEvent) sut.getUncommittedChanges().get(0);
        assertThat(issueCommentHiddenEvent.getId()).isEqualTo(ISSUE_ID_PLAIN);
        assertThat(issueCommentHiddenEvent.getCommentId()).isEqualTo(FIRST_COMMENT_ID_PLAIN);
    }

    @Test
    void commentCanNotBeHiddenWhenIssueIsClosed() {
        // Arrange
        var sut = createClosedIssueWithFirstComment();

        // Assert
        assertThatIssueClosedExceptionIsThrownBy(() -> sut.hideComment(FIRST_COMMENT_ID));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void notExistingCommentCanNotBeHidden() {
        // Arrange
        var sut = createIssue();

        // Assert
        assertThatCommentNotFoundExceptionIsThrownBy(() -> sut.hideComment(FIRST_COMMENT_ID));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void hiddenCommentCanNotBeHiddenAgain() {
        // Arrange
        var sut = createIssueWithFirstComment();
        sut.hideComment(FIRST_COMMENT_ID);
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(CommentHiddenException.class)
                .isThrownBy(() -> sut.hideComment(FIRST_COMMENT_ID));
        assertThatNoEventsAreRaised(sut);
    }

    @ParameterizedTest
    @EnumSource(VoteType.class)
    void votingCommentRaisesIssueCommentVotedEvent(VoteType voteType) {
        // Arrange
        var sut = createIssueWithFirstComment();
        var vote = new Vote(FIRST_VOTER_ID, voteType);

        // Act
        sut.voteComment(FIRST_COMMENT_ID, vote);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, IssueCommentVotedEvent.class);

        var issueCommentVotedEvent = (IssueCommentVotedEvent) sut.getUncommittedChanges().get(0);
        assertThat(issueCommentVotedEvent.getId()).isEqualTo(ISSUE_ID_PLAIN);
        assertThat(issueCommentVotedEvent.getCommentId()).isEqualTo(FIRST_COMMENT_ID_PLAIN);
        assertThat(issueCommentVotedEvent.getVoterId()).isEqualTo(FIRST_VOTER_ID_PLAIN);
        assertThat(issueCommentVotedEvent.getVoteType()).isEqualTo(voteType);
    }

    @Test
    void commentCanNotBeVotedWhenIssueIsClosed() {
        // Arrange
        var sut = createClosedIssueWithFirstComment();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);

        // Assert
        assertThatIssueClosedExceptionIsThrownBy(() -> sut.voteComment(FIRST_COMMENT_ID, vote));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void notExistingCommentCanNotBeVoted() {
        // Arrange
        var sut = createIssue();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);

        // Assert
        assertThatCommentNotFoundExceptionIsThrownBy(() -> sut.voteComment(FIRST_COMMENT_ID, vote));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void voterCanVoteCommentWithDifferentVoteTypes() {
        // Arrange
        var sut = createIssueWithFirstComment();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        var anotherVote = new Vote(FIRST_VOTER_ID, VoteType.DOWN);

        // Act
        sut.voteComment(FIRST_COMMENT_ID, vote);
        sut.voteComment(FIRST_COMMENT_ID, anotherVote);

        // Assert
        assertThatAmountOfRaisedEventsIsEqualTo(sut, 2);
    }

    @Test
    void voterCanNotVoteCommentWithTheSameVoteType() {
        // Arrange
        var sut = createIssueWithFirstComment();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        sut.voteComment(FIRST_COMMENT_ID, vote);
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(VoteAlreadyExistsException.class)
                .isThrownBy(() -> sut.voteComment(FIRST_COMMENT_ID, vote));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void commentCanHaveManyVotes() {
        // Arrange
        var sut = createIssueWithFirstComment();
        var firstVote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        var secondVote = new Vote(SECOND_VOTER_ID, VoteType.UP);

        // Act
        sut.voteComment(FIRST_COMMENT_ID, firstVote);
        sut.voteComment(FIRST_COMMENT_ID, secondVote);

        // Assert
        assertThatAmountOfRaisedEventsIsEqualTo(sut, 2);
    }

    @Test
    void votingIssueRaisesIssueVotedEvent() {
        // Arrange
        var sut = createIssue();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);

        // Act
        sut.vote(vote);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, IssueVotedEvent.class);

        var issueVotedEvent = (IssueVotedEvent) sut.getUncommittedChanges().get(0);
        assertThat(issueVotedEvent.getId()).isEqualTo(ISSUE_ID_PLAIN);
        assertThat(issueVotedEvent.getVoterId()).isEqualTo(FIRST_VOTER_ID_PLAIN);
        assertThat(issueVotedEvent.getVoteType()).isEqualTo(VoteType.UP);
    }

    @Test
    void closedIssueCanNotBeVoted() {
        // Arrange
        var sut = createClosedIssue();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);

        // Assert
        assertThatIssueClosedExceptionIsThrownBy(() -> sut.vote(vote));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void voterCanVoteIssueWithDifferentVoteTypes() {
        // Arrange
        var sut = createIssue();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        var anotherVote = new Vote(FIRST_VOTER_ID, VoteType.DOWN);

        // Act
        sut.vote(vote);
        sut.vote(anotherVote);

        // Assert
        assertThatAmountOfRaisedEventsIsEqualTo(sut, 2);
    }

    @Test
    void voterCanNotVoteIssueWithTheSameVotesTypes() {
        // Arrange
        var sut = createIssue();
        var vote = new Vote(FIRST_VOTER_ID, VoteType.UP);
        sut.vote(vote);
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(VoteAlreadyExistsException.class)
                .isThrownBy(() -> sut.vote(vote));
        assertThatNoEventsAreRaised(sut);
    }

    private Issue createIssue() {
        var id = new IssueId(ISSUE_UUID);
        var content = new IssueContent(ISSUE_CONTENT_PLAIN);
        var name = new IssueName(ISSUE_NAME_PLAIN);
        var issue = Issue.open(id, ISSUE_TYPE, content, name);

        issue.markChangesAsCommitted();

        return issue;
    }

    private Issue createClosedIssue() {
        var issue = createIssue();
        issue.close();
        issue.markChangesAsCommitted();

        return issue;
    }

    private Issue createIssueWithFirstComment() {
        var issue = createIssue();
        var firstComment = createFirstComment();

        issue.comment(firstComment);
        issue.markChangesAsCommitted();

        return issue;
    }

    private Issue createClosedIssueWithFirstComment() {
        var issue = createIssue();
        var firstComment = createFirstComment();

        issue.comment(firstComment);
        issue.close();
        issue.markChangesAsCommitted();

        return issue;
    }

    private Comment createFirstComment() {
        var id = new CommentId(FIRST_COMMENT_UUID);
        var content = new CommentContent(FIRST_COMMENT_CONTENT_PLAIN);

        return new Comment(id, content);
    }

    private Comment createSecondComment() {
        var id = new CommentId(SECOND_COMMENT_UUID);
        var content = new CommentContent("Example second comment content");

        return new Comment(id, content);
    }

    private void assertThatTheOnlyRaisedEventIs(Issue issue, Class<?> clazz) {
        assertThat(issue.getUncommittedChanges().size()).isEqualTo(1);

        var event = issue.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(clazz);
    }

    private void assertThatNoEventsAreRaised(Issue issue) {
        assertThatAmountOfRaisedEventsIsEqualTo(issue, 0);
    }

    private void assertThatAmountOfRaisedEventsIsEqualTo(Issue issue, int amount) {
        assertThat(issue.getUncommittedChanges().size()).isEqualTo(amount);
    }

    private void assertThatIssueClosedExceptionIsThrownBy(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatExceptionOfType(IssueClosedException.class).isThrownBy(throwingCallable);
    }

    private void assertThatCommentNotFoundExceptionIsThrownBy(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatExceptionOfType(CommentNotFoundException.class).isThrownBy(throwingCallable);
    }
}
