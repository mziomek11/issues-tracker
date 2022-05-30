package com.mateuszziomek.issuestracker.users.command.infrastructure.gateway;

import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.ReactiveUserRestClient;
import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserEmailUnavailableException;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserServiceUnavailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserGatewayImplTest {
    private static final String USER_EMAIL_PLAIN = "example@mail.com";
    private static final UserEmail USER_EMAIL = new UserEmail(USER_EMAIL_PLAIN);

    @Test
    void exceptionIsThrownWhenUserWithGivenEmailExists() {
        // Arrange
        var restClient = mock(ReactiveUserRestClient.class);
        var sut = new UserGatewayImpl(restClient);

        when(restClient.getUsers(argThat(p -> p.getEmail().equals(USER_EMAIL_PLAIN))))
                .thenReturn(Flux.just(ListUser.builder().email(USER_EMAIL_PLAIN).id(UUID.randomUUID()).build()));

        // Assert
        assertThatExceptionOfType(UserEmailUnavailableException.class)
                .isThrownBy(() -> sut.ensureUserEmailIsAvailable(USER_EMAIL));
    }

    @Test
    void exceptionIsThrownWhenUserServiceIsUnavailable() {
        // Arrange
        var restClient = mock(ReactiveUserRestClient.class);
        var sut = new UserGatewayImpl(restClient);

        when(restClient.getUsers(argThat(p -> p.getEmail().equals(USER_EMAIL_PLAIN))))
                .thenReturn(Flux.error(new com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.exception.UserServiceUnavailableException()));

        // Assert
        assertThatExceptionOfType(UserServiceUnavailableException.class)
                .isThrownBy(() -> sut.ensureUserEmailIsAvailable(USER_EMAIL));
    }
}
