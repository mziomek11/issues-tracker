package com.mateuszziomek.issuestracker.organizations.command.application.command.handler;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandHandlerIntegrationTest;
import com.mateuszziomek.issuestracker.organizations.command.application.service.MemberService;
import com.mateuszziomek.issuestracker.organizations.command.projection.Member;
import com.mateuszziomek.issuestracker.organizations.command.projection.MemberRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandData.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class InviteOrganizationMemberCommandHandlerIntegrationTest extends OrganizationCommandHandlerIntegrationTest {
    @Test
    void invitingOrganizationMemberSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var memberRepository = mock(MemberRepository.class);
        var sut = createHandler(eventProducer, eventStoreRepository, memberRepository);
        var member = Member.register(
                UserRegisteredEvent
                        .builder()
                        .userActivationToken(UUID.randomUUID())
                        .userEmail(INVITED_MEMBER_EMAIL)
                        .userHashedPassword("password")
                        .userId(INVITED_MEMBER_UUID)
                        .build()
        );

        when(eventStoreRepository.findByAggregateId(ORGANIZATION_ID))
                .thenReturn(List.of(ORGANIZATION_CREATED_EVENT_MODEL));

        when(memberRepository.findByEmail(argThat(p -> p.equals(INVITED_MEMBER_EMAIL))))
                .thenReturn(Optional.of(member));

        // Act
        sut.handle(INVITE_ORGANIZATION_MEMBER_COMMAND);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq("OrganizationMemberInvitedEvent"),
                argThat(event -> hasOrganizationMemberInvitedEventCorrectedData((OrganizationMemberInvitedEvent) event))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals("OrganizationMemberInvitedEvent")
                        && eventModel.aggregateId().getValue().equals(ORGANIZATION_UUID))
                        && hasOrganizationMemberInvitedEventCorrectedData((OrganizationMemberInvitedEvent) eventModel.eventData())
                )
        );
    }

    private InviteOrganizationMemberCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository,
            MemberRepository memberRepository
    ) {
        var eventStore = createEventStore(eventStoreRepository, eventProducer);
        var eventSourcingHandler = createSourcingHandler(eventStore);
        var memberService = new MemberService(memberRepository);

        return new InviteOrganizationMemberCommandHandler(eventSourcingHandler, memberService);
    }

    private boolean hasOrganizationMemberInvitedEventCorrectedData(OrganizationMemberInvitedEvent event) {
        return (
                event.getId().equals(INVITE_ORGANIZATION_MEMBER_COMMAND.getOrganizationId().getValue())
                && event.getMemberId().equals(INVITED_MEMBER_UUID)
        );
    }
}