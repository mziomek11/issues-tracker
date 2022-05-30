package com.mateuszziomek.issuestracker.organizations.command.infrastructure.gateway;

import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception.MemberNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception.MemberServiceUnavailableException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberEmail;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.ReactiveUserRestClient;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.exception.UserServiceUnavailableException;
import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MemberGatewayImplTest {
    private static final String MEMBER_EMAIL_PLAIN = "example@mail.com";
    private static final MemberEmail MEMBER_EMAIL = new MemberEmail(MEMBER_EMAIL_PLAIN);

    @Test
    void idOfActivatedUserWithGivenEmailIsReturned() {
        // Arrange
        var restClient = mock(ReactiveUserRestClient.class);
        var userUUID = UUID.randomUUID();
        var sut = new MemberGatewayImpl(restClient);
        var user = ListUser
                .builder()
                .email(MEMBER_EMAIL_PLAIN)
                .id(userUUID)
                .build();

        when(restClient.getUsers(argThat(p -> p.getStatus().equals(UserStatus.ACTIVATED) && p.getEmail().equals(MEMBER_EMAIL_PLAIN))))
                .thenReturn(Flux.just(user));

        // Act
        var result = sut.getMemberId(MEMBER_EMAIL);

        // Assert
        assertThat(result.getValue()).isEqualTo(userUUID);
    }

    @Test
    void exceptionIsThrownWhenUserWithGivenEmailDoesNotExist() {
        // Arrange
        var restClient = mock(ReactiveUserRestClient.class);
        var sut = new MemberGatewayImpl(restClient);

        when(restClient.getUsers(any())).thenReturn(Flux.empty());

        // Assert
        assertThatExceptionOfType(MemberNotFoundException.class).isThrownBy(() -> sut.getMemberId(MEMBER_EMAIL));
    }

    @Test
    void exceptionIsThrownWhenMemberServiceIsUnavailable() {
        // Arrange
        var restClient = mock(ReactiveUserRestClient.class);
        var sut = new MemberGatewayImpl(restClient);

        when(restClient.getUsers(any())).thenReturn(Flux.error(new UserServiceUnavailableException()));

        // Assert
        assertThatExceptionOfType(MemberServiceUnavailableException.class).isThrownBy(() -> sut.getMemberId(MEMBER_EMAIL));
    }
}
