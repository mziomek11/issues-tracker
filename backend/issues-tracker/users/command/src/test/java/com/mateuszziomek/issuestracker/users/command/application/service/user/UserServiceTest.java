package com.mateuszziomek.issuestracker.users.command.application.service.user;

import com.mateuszziomek.issuestracker.users.command.application.service.user.exception.UserEmailUnavailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import com.mateuszziomek.issuestracker.users.command.projection.User;
import com.mateuszziomek.issuestracker.users.command.projection.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Test
    void throwsExceptionWhenUserEmailIsUnavailable() {
        // Arrange
        var userRepository = mock(UserRepository.class);
        var sut = new UserService(userRepository);
        var plainEmail = "example@mail.com";
        var email = new UserEmail(plainEmail);

        when(userRepository.findByEmail(argThat(e -> e.equals(plainEmail))))
                .thenReturn(Optional.of(new User()));

        // Assert
        assertThatExceptionOfType(UserEmailUnavailableException.class)
                .isThrownBy(() -> sut.ensureUserEmailIsAvailable(email));
        verify(userRepository, times(1)).findByEmail(argThat(e -> e.equals(plainEmail)));
    }

    @Test
    void doesNothingWhenUserEmailIsAvailable() {
        // Arrange
        var userRepository = mock(UserRepository.class);
        var sut = new UserService(userRepository);
        var plainEmail = "example@mail.com";
        var email = new UserEmail(plainEmail);

        when(userRepository.findByEmail(argThat(e -> e.equals(plainEmail))))
                .thenReturn(Optional.empty());

        // Act
        sut.ensureUserEmailIsAvailable(email);

        // Assert
        verify(userRepository, times(1)).findByEmail(argThat(e -> e.equals(plainEmail)));
    }
}
