package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandHandlerTest;
import com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.OrganizationGatewayExceptionArgumentProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

class OpenIssueCommandHandlerTest extends IssueCommandHandlerTest {
    @ParameterizedTest
    @ArgumentsSource(OrganizationGatewayExceptionArgumentProvider.class)
    void eventSourcingHandlerIsNotCalledWhenGatewayThrows(RuntimeException gatewayException) {
        // Arrange
        var eventSourcingHandler = mock(EventSourcingHandler.class);
        var organizationGateway = createOrganizationGatewayMock(gatewayException);
        var sut = new OpenIssueCommandHandler(eventSourcingHandler, organizationGateway);

        // Assert
        assertThatExceptionOfType(gatewayException.getClass()).isThrownBy(() -> sut.handle(OPEN_ISSUE_COMMAND));
        verify(eventSourcingHandler, times(0)).save(any());
    }
}
