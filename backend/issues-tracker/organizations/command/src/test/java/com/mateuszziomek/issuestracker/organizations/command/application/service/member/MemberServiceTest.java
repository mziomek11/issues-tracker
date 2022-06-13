package com.mateuszziomek.issuestracker.organizations.command.application.service.member;

import com.mateuszziomek.issuestracker.organizations.command.application.service.MemberService;
import com.mateuszziomek.issuestracker.organizations.command.application.service.exception.MemberNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberEmail;
import com.mateuszziomek.issuestracker.organizations.command.projection.Member;
import com.mateuszziomek.issuestracker.organizations.command.projection.MemberRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {
    @Test
    void throwsExceptionWhenMemberWithGivenEmailDoesNotExist() {
        // Arrange
        var memberRepository = mock(MemberRepository.class);
        var sut = new MemberService(memberRepository);
        var plainEmail = "example@mail.com";
        var email = new MemberEmail(plainEmail);

        when(memberRepository.findByEmail(argThat(e -> e.equals(plainEmail))))
                .thenReturn(Optional.empty());

        // Act
        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> sut.getMemberId(email));
        verify(memberRepository, times(1)).findByEmail(argThat(e -> e.equals(plainEmail)));
    }

    @Test
    void returnsMemberId() {
        // Arrange
        var memberRepository = mock(MemberRepository.class);
        var sut = new MemberService(memberRepository);
        var plainEmail = "example@mail.com";
        var email = new MemberEmail(plainEmail);
        var memberUUID = UUID.randomUUID();
        var userRegisteredEvent = UserRegisteredEvent
                .builder()
                .userId(memberUUID)
                .userEmail(plainEmail)
                .userHashedPassword("passowrd")
                .userActivationToken(UUID.randomUUID())
                .build();
        var member = Member.register(userRegisteredEvent);

        when(memberRepository.findByEmail(argThat(e -> e.equals(plainEmail))))
                .thenReturn(Optional.of(member));

        // Act
        var memberId = sut.getMemberId(email);

        // Assert
        assertThat(memberId.getValue()).isEqualTo(memberUUID);
    }
}
