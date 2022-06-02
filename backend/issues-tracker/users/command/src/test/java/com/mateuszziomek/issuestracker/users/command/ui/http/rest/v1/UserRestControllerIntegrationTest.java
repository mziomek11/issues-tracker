package com.mateuszziomek.issuestracker.users.command.ui.http.rest.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateuszziomek.cqrs.command.dispatcher.CommandDispatcher;
import com.mateuszziomek.issuestracker.shared.readmodel.ObjectId;
import com.mateuszziomek.issuestracker.users.command.application.command.ActivateUserCommand;
import com.mateuszziomek.issuestracker.users.command.application.command.RegisterUserCommand;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.user.ActivateUserDto;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.user.RegisterUserDto;
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

@WebMvcTest(UserRestController.class)
class UserRestControllerIntegrationTest {
    private static final UUID USER_UUID = UUID.randomUUID();
    private static final String URL_BASE = "/api/v1/user-management/users";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommandDispatcher commandDispatcher;

    @Test
    void userCanBeRegistered() throws Exception {
        // Arrange
        var dto = new RegisterUserDto("email@mail.com", "qwerty123");
        var request = MockMvcRequestBuilders
                .post(URL_BASE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(RegisterUserCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        var userId = command.getUserId().getValue();
        assertThat(userId).isNotNull();
        assertThat(command.getUserEmail().text()).isEqualTo("email@mail.com");
        assertThat(command.getUserPlainPassword().text()).isEqualTo("qwerty123");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(objectMapper.readValue(response.getContentAsByteArray(), ObjectId.class).getId()).isEqualTo(userId);
    }

    @Test
    void userCanBeActivated() throws Exception {
        // Arrange
        var activationTokenUUID = UUID.randomUUID();
        var dto = new ActivateUserDto(activationTokenUUID);
        var request = MockMvcRequestBuilders
                .post(URL_BASE + String.format("/%s/activation-token", USER_UUID))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto));

        // Act
        var response = mvc.perform(request).andReturn().getResponse();

        // Assert
        var captor = ArgumentCaptor.forClass(ActivateUserCommand.class);
        verify(commandDispatcher, times(1)).dispatch(captor.capture());

        var command = captor.getValue();
        assertThat(command.getUserId().getValue()).isEqualTo(USER_UUID);
        assertThat(command.getUserActivationToken().value()).isEqualTo(activationTokenUUID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEmpty();
    }
}
