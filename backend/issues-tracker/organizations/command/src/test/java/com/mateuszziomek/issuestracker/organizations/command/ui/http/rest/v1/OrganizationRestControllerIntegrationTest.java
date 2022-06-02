package com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateuszziomek.cqrs.command.dispatcher.CommandDispatcher;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.JoinOrganizationMemberCommand;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.organization.CreateOrganizationDto;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.organization.CreateOrganizationProjectDto;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.organization.InviteOrganizationMemberDto;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.readmodel.ObjectId;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@WebMvcTest(OrganizationRestController.class)
class OrganizationRestControllerIntegrationTest {
    private final static UUID ORGANIZATION_UUID = UUID.randomUUID();
    private final static UUID USER_UUID = UUID.randomUUID();
    private final static String URL_BASE = "/api/v1/organization-management/organizations";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommandDispatcher commandDispatcher;


    @Test
    void organizationCanBeCreated() throws Exception {
        // Arrange
        var dto = new CreateOrganizationDto("Example name");
        var request = MockMvcRequestBuilders
                .post(URL_BASE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, USER_UUID)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(CreateOrganizationCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        var organizationId = command.getOrganizationId().getValue();
        assertThat(organizationId).isNotNull();
        assertThat(command.getOrganizationOwnerId().getValue()).isEqualTo(USER_UUID);
        assertThat(command.getOrganizationName().text()).isEqualTo("Example name");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(objectMapper.readValue(response.getContentAsByteArray(), ObjectId.class).getId()).isEqualTo(organizationId);
    }

    @Test
    void organizationMemberCanBeInvited() throws Exception {
        // Arrange
        var dto = new InviteOrganizationMemberDto("example@mail.com");
        var request = MockMvcRequestBuilders
                .post(URL_BASE + String.format("/%s/invitations", ORGANIZATION_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, USER_UUID)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(InviteOrganizationMemberCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        assertThat(command.getOrganizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationOwnerId().getValue()).isEqualTo(USER_UUID);
        assertThat(command.getMemberEmail().text()).isEqualTo("example@mail.com");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsByteArray()).isEmpty();
    }

    @Test
    void organizationMemberCanJoin() throws Exception {
        // Arrange
        var request = MockMvcRequestBuilders
                .post(URL_BASE + String.format("/%s/members", ORGANIZATION_UUID))
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, USER_UUID);

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(JoinOrganizationMemberCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        assertThat(command.getOrganizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getMemberId().getValue()).isEqualTo(USER_UUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEmpty();
    }

    @Test
    void organizationProjectCanBeCreated() throws Exception {
        // Arrange
        var dto = new CreateOrganizationProjectDto("Example project");
        var request = MockMvcRequestBuilders
                .post(URL_BASE + String.format("/%s/projects", ORGANIZATION_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, USER_UUID)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(CreateOrganizationProjectCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        var projectId = command.getProjectId();
        assertThat(command.getProjectId()).isNotNull();
        assertThat(command.getOrganizationId().getValue()).isEqualTo(ORGANIZATION_UUID);
        assertThat(command.getOrganizationOwnerId().getValue()).isEqualTo(USER_UUID);
        assertThat(command.getProjectName().text()).isEqualTo("Example project");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(objectMapper.readValue(response.getContentAsByteArray(), ObjectId.class).getId()).isEqualTo(projectId.getValue());
    }
}
