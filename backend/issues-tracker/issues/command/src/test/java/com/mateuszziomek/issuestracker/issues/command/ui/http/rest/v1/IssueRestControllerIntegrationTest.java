package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateuszziomek.cqrs.command.dispatcher.CommandDispatcher;
import com.mateuszziomek.issuestracker.issues.command.application.command.*;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.VoteType;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.readmodel.ObjectId;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.issue.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(IssueRestController.class)
class IssueRestControllerIntegrationTest {
    private final static UUID ORGANIZATION_UUID = UUID.randomUUID();
    private final static UUID PROJECT_UUID = UUID.randomUUID();
    private final static UUID MEMBER_UUID = UUID.randomUUID();
    private final static UUID ISSUE_UUID = UUID.randomUUID();
    private final static UUID COMMENT_UUID = UUID.randomUUID();
    private final static String URL_BASE = String.format(
            "/api/v1/issue-management/organizations/%s/projects/%s/issues",
            ORGANIZATION_UUID,
            PROJECT_UUID
    );

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommandDispatcher commandDispatcher;

    @Test
    void issueCanBeOpened() throws Exception {
        // Arrange
        var dto = new OpenIssueDto("Example name", "Example content", IssueType.BUG);
        var request = MockMvcRequestBuilders
                .post(URL_BASE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, MEMBER_UUID)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(OpenIssueCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        var issueId = command.getIssueId().getValue();
        assertThat(issueId).isNotNull();
        assertThat(command.getIssueContent().text()).isEqualTo("Example content");
        assertThat(command.getIssueName().text()).isEqualTo("Example name");
        assertThat(command.getIssueType()).isEqualTo(IssueType.BUG);
        assertThat(command.getOrganizationDetails().organizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationDetails().projectId().getValue()).isEqualTo(PROJECT_UUID);
        assertThat(command.getOrganizationDetails().memberId().getValue()).isEqualTo(MEMBER_UUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(objectMapper.readValue(response.getContentAsByteArray(), ObjectId.class).getId()).isEqualTo(issueId);
    }

    @Test
    void issueCanBeClosed() throws Exception {
        // Arrange
        var request = MockMvcRequestBuilders
                .delete(URL_BASE + String.format("/%s", ISSUE_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, MEMBER_UUID);

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(CloseIssueCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        assertThat(command.getIssueId().getValue()).isEqualTo(ISSUE_UUID);
        assertThat(command.getOrganizationDetails().organizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationDetails().projectId().getValue()).isEqualTo(PROJECT_UUID);
        assertThat(command.getOrganizationDetails().memberId().getValue()).isEqualTo(MEMBER_UUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEmpty();
    }

    @Test
    void issueCanBeRenamed() throws Exception {
        // Arrange
        var dto = new RenameIssueDto("Changed name");
        var request = MockMvcRequestBuilders
                .patch(URL_BASE + String.format("/%s/name", ISSUE_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, MEMBER_UUID)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(RenameIssueCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        assertThat(command.getIssueId().getValue()).isEqualTo(ISSUE_UUID);
        assertThat(command.getIssueName().text()).isEqualTo("Changed name");
        assertThat(command.getOrganizationDetails().organizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationDetails().projectId().getValue()).isEqualTo(PROJECT_UUID);
        assertThat(command.getOrganizationDetails().memberId().getValue()).isEqualTo(MEMBER_UUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEmpty();
    }

    @Test
    void issueTypeCanBeChanged() throws Exception {
        // Arrange
        var dto = new ChangeIssueTypeDto(IssueType.BUG);
        var request = MockMvcRequestBuilders
                .patch(URL_BASE + String.format("/%s/type", ISSUE_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, MEMBER_UUID)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(ChangeIssueTypeCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        assertThat(command.getIssueId().getValue()).isEqualTo(ISSUE_UUID);
        assertThat(command.getIssueType()).isEqualTo(IssueType.BUG);
        assertThat(command.getOrganizationDetails().organizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationDetails().projectId().getValue()).isEqualTo(PROJECT_UUID);
        assertThat(command.getOrganizationDetails().memberId().getValue()).isEqualTo(MEMBER_UUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEmpty();
    }

    @Test
    void issueContentCanBeChanged() throws Exception {
        // Arrange
        var dto = new ChangeIssueContentDto("Changed content");
        var request = MockMvcRequestBuilders
                .patch(URL_BASE + String.format("/%s/content", ISSUE_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, MEMBER_UUID)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(ChangeIssueContentCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        assertThat(command.getIssueId().getValue()).isEqualTo(ISSUE_UUID);
        assertThat(command.getIssueContent().text()).isEqualTo("Changed content");
        assertThat(command.getOrganizationDetails().organizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationDetails().projectId().getValue()).isEqualTo(PROJECT_UUID);
        assertThat(command.getOrganizationDetails().memberId().getValue()).isEqualTo(MEMBER_UUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEmpty();
    }

    @Test
    void issueCanBeVoted() throws Exception {
        // Arrange
        var dto = new VoteIssueDto(VoteType.UP);
        var request = MockMvcRequestBuilders
                .post(URL_BASE + String.format("/%s/votes", ISSUE_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, MEMBER_UUID)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(VoteIssueCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        assertThat(command.getIssueId().getValue()).isEqualTo(ISSUE_UUID);
        assertThat(command.getVoteType()).isEqualTo(VoteType.UP);
        assertThat(command.getOrganizationDetails().organizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationDetails().projectId().getValue()).isEqualTo(PROJECT_UUID);
        assertThat(command.getOrganizationDetails().memberId().getValue()).isEqualTo(MEMBER_UUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEmpty();
    }

    @Test
    void issueCanBeCommented() throws Exception {
        // Arrange
        var dto = new CommentIssueDto("Example content");
        var request = MockMvcRequestBuilders
                .post(URL_BASE + String.format("/%s/comments", ISSUE_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, MEMBER_UUID)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(CommentIssueCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        var commentId = command.getCommentId().getValue();
        assertThat(commentId).isNotNull();
        assertThat(command.getCommentContent().text()).isEqualTo("Example content");
        assertThat(command.getIssueId().getValue()).isEqualTo(ISSUE_UUID);
        assertThat(command.getOrganizationDetails().organizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationDetails().projectId().getValue()).isEqualTo(PROJECT_UUID);
        assertThat(command.getOrganizationDetails().memberId().getValue()).isEqualTo(MEMBER_UUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(objectMapper.readValue(response.getContentAsByteArray(), ObjectId.class).getId()).isEqualTo(commentId);
    }

    @Test
    void issueCommentCanBeHidden() throws Exception {
        // Arrange
        var request = MockMvcRequestBuilders
                .delete(URL_BASE + String.format("/%s/comments/%s", ISSUE_UUID, COMMENT_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, MEMBER_UUID);

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(HideIssueCommentCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        assertThat(command.getCommentId().getValue()).isEqualTo(COMMENT_UUID);
        assertThat(command.getIssueId().getValue()).isEqualTo(ISSUE_UUID);
        assertThat(command.getOrganizationDetails().organizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationDetails().projectId().getValue()).isEqualTo(PROJECT_UUID);
        assertThat(command.getOrganizationDetails().memberId().getValue()).isEqualTo(MEMBER_UUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEmpty();
    }

    @Test
    void issueCommentContentCanBeChanged() throws Exception {
        // Arrange
        var dto = new ChangeIssueCommentContentDto("Changed content");
        var request = MockMvcRequestBuilders
                .delete(URL_BASE + String.format("/%s/comments/%s/content", ISSUE_UUID, COMMENT_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, MEMBER_UUID)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(ChangeIssueCommentContentCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        assertThat(command.getCommentId().getValue()).isEqualTo(COMMENT_UUID);
        assertThat(command.getCommentContent().text()).isEqualTo("Changed content");
        assertThat(command.getIssueId().getValue()).isEqualTo(ISSUE_UUID);
        assertThat(command.getOrganizationDetails().organizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationDetails().projectId().getValue()).isEqualTo(PROJECT_UUID);
        assertThat(command.getOrganizationDetails().memberId().getValue()).isEqualTo(MEMBER_UUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEmpty();
    }

    @Test
    void issueCommentCanBeVoted() throws Exception {
        // Arrange
        var dto = new VoteIssueCommentDto(VoteType.UP);
        var request = MockMvcRequestBuilders
                .post(URL_BASE + String.format("/%s/comments/%s/votes", ISSUE_UUID, COMMENT_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, MEMBER_UUID)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(VoteIssueCommentCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        assertThat(command.getCommentId().getValue()).isEqualTo(COMMENT_UUID);
        assertThat(command.getVoteType()).isEqualTo(VoteType.UP);
        assertThat(command.getIssueId().getValue()).isEqualTo(ISSUE_UUID);
        assertThat(command.getOrganizationDetails().organizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationDetails().projectId().getValue()).isEqualTo(PROJECT_UUID);
        assertThat(command.getOrganizationDetails().memberId().getValue()).isEqualTo(MEMBER_UUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEmpty();
    }
}
