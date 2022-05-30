package com.mateuszziomek.issuestracker.organizations.command.application.command.handler;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandHandlerIntegrationTest;
import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.MemberGateway;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandData.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class InviteOrganizationMemberCommandHandlerIntegrationTest extends OrganizationCommandHandlerIntegrationTest {
    @Test
    void invitingOrganizationMemberSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var memberGateway = mock(MemberGateway.class);
        var sut = createHandler(eventProducer, eventStoreRepository, memberGateway);

        when(eventStoreRepository.findByAggregateId(ORGANIZATION_ID))
                .thenReturn(List.of(ORGANIZATION_CREATED_EVENT_MODEL));

        when(memberGateway.getMemberId(argThat(email -> email.text().equals(INVITED_MEMBER_EMAIL))))
                .thenReturn(new MemberId(INVITED_MEMBER_UUID));

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
            MemberGateway memberGateway
    ) {
        var eventStore = createEventStore(eventStoreRepository, eventProducer);
        var eventSourcingHandler = createSourcingHandler(eventStore);

        return new InviteOrganizationMemberCommandHandler(eventSourcingHandler, memberGateway);
    }

    private boolean hasOrganizationMemberInvitedEventCorrectedData(OrganizationMemberInvitedEvent event) {
        return (
                event.getId().equals(INVITE_ORGANIZATION_MEMBER_COMMAND.getOrganizationId().getValue())
                && event.getMemberId().equals(INVITED_MEMBER_UUID)
        );
    }
}