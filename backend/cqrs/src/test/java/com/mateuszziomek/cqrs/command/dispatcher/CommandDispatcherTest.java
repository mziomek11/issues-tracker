package com.mateuszziomek.cqrs.command.dispatcher;

import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.cqrs.domain.AggregateConcurrencyException;
import com.mateuszziomek.cqrs.domain.AggregateId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandDispatcherTest {
    @Test
    void commandCanHaveOnlyOneHandler() {
        // Arrange
        var firstHandler = new TestCommandHandler();
        var secondHandler = new TestCommandHandler();
        var sut = new CommandDispatcher();
        sut.registerHandler(TestCommand.class, firstHandler);

        // Assert
        assertThatExceptionOfType(TooManyCommandHandlersException.class)
                .isThrownBy(() -> sut.registerHandler(TestCommand.class, secondHandler));
    }

    @Test
    void notRegisteredCommandCanNotBeDispatched() {
        // Arrange
        var command = new TestCommand();
        var sut = new CommandDispatcher();

        // Assert
        assertThatExceptionOfType(CommandHandlerNotFoundException.class)
                .isThrownBy(() -> sut.dispatch(command));
    }

    @Test
    void commandIsPassedToRegisteredHandler() {
        // Arrange
        var command = new TestCommand();
        var handler = mock(TestCommandHandler.class);
        var sut = new CommandDispatcher();
        sut.registerHandler(TestCommand.class, handler);

        // Act
        sut.dispatch(command);

        // Assert
        verify(handler, times(1)).handle(command);
    }

    @Test
    void commandKeepsDispatchingUntilAggregateConcurrencyExceptionIsNotPresent() {
        // Arrange
        var command = new TestCommand();
        var handler = mock(TestCommandHandler.class);
        var sut = new CommandDispatcher();
        sut.registerHandler(TestCommand.class, handler);

        doThrow(new AggregateConcurrencyException(new TestAggregateId()))
                .doThrow(new AggregateConcurrencyException(new TestAggregateId()))
                .doNothing()
                .when(handler)
                .handle(command);

        // Act
        sut.dispatch(command);

        // Assert
        verify(handler, times(3)).handle(command);
    }

    private static class TestCommand { }

    private static class TestCommandHandler implements CommandHandler<TestCommand> {
        @Override
        public void handle(TestCommand command) { }
    }

    private static class TestAggregateId extends AggregateId {
        protected TestAggregateId() {
            super(UUID.randomUUID());
        }
    }
}
