package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandHandlerTest;
import com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.OrganizationGatewayExceptionArgumentProvider;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Optional;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VoteIssueCommentCommandHandlerTest extends IssueCommandHandlerTest {
    @ParameterizedTest
    @ArgumentsSource(OrganizationGatewayExceptionArgumentProvider.class)
    void eventSourcingHandlerIsNotCalledWhenGatewayThrows(RuntimeException gatewayException) {
        // Arrange
        var eventSourcingHandler = mock(EventSourcingHandler.class);
        var organizationGateway = createOrganizationGatewayExceptionMock(gatewayException);
        var sut = new VoteIssueCommentCommandHandler(eventSourcingHandler, organizationGateway);

        // Assert
        assertThatExceptionOfType(gatewayException.getClass()).isThrownBy(() -> sut.handle(VOTE_ISSUE_COMMENT_COMMAND));
        verify(eventSourcingHandler, times(0)).save(any());
    }

    @Test
    void eventSourcingHandlerIsNotCalledWhenIssueDoesNotExist() {
        // Arrange
        var eventSourcingHandler = mock(EventSourcingHandler.class);
        when(eventSourcingHandler.getById(any())).thenReturn(Optional.empty());
        var organizationGateway = mock(OrganizationGateway.class);
        var sut = new VoteIssueCommentCommandHandler(eventSourcingHandler, organizationGateway);

        // Assert
        assertThatExceptionOfType(IssueNotFoundException.class).isThrownBy(() -> sut.handle(VOTE_ISSUE_COMMENT_COMMAND));
        verify(eventSourcingHandler, times(0)).save(any());
    }
}