package com.mateuszziomek.issuestracker.organizations.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.organizations.command.application.service.MemberService;
import com.mateuszziomek.issuestracker.organizations.command.application.service.exception.MemberNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberEmail;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.Organization;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationName;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationOwner;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandData.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class InviteOrganizationMemberCommandHandlerTest {
    @Test
    void eventSourcingHandlerIsNotCalledWhenOrganizationDoesNotExist() {
        // Arrange
        var eventSourcingHandler = mock(EventSourcingHandler.class);
        when(eventSourcingHandler.getById(any())).thenReturn(Optional.empty());
        var sut = new InviteOrganizationMemberCommandHandler(eventSourcingHandler, mock(MemberService.class));

        // Assert
        assertThatExceptionOfType(OrganizationNotFoundException.class)
                .isThrownBy(() -> sut.handle(INVITE_ORGANIZATION_MEMBER_COMMAND));
        verify(eventSourcingHandler, times(0)).save(any());
    }

    @Test
    void eventSourcingHandlerIsNotCalledWhenMemberDoesNotExists() {
        // Arrange
        var eventSourcingHandler = mock(EventSourcingHandler.class);
        var memberService = mock(MemberService.class);
        var sut = new InviteOrganizationMemberCommandHandler(eventSourcingHandler, memberService);

        var organization = Organization.create(
                new OrganizationId(UUID.randomUUID()),
                new OrganizationOwner(new MemberId(UUID.randomUUID())),
                new OrganizationName("Example name")
        );

        when(eventSourcingHandler.getById(any())).thenReturn(Optional.of(organization));
        when(memberService.getMemberId(argThat(memberEmail -> memberEmail.text().equals(INVITED_MEMBER_EMAIL))))
                .thenThrow(new MemberNotFoundException(new MemberEmail(INVITED_MEMBER_EMAIL)));

        // Assert
        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> sut.handle(INVITE_ORGANIZATION_MEMBER_COMMAND));
        verify(eventSourcingHandler, times(0)).save(any());
    }
}
