package com.mateuszziomek.cqrs.event.dispatcher;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventHandler;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class EventDispatcherTest {
    @Test
    void eventCanHaveOnlyOneHandler() {
        // Arrange
        var firstHandler = new TestEventHandler();
        var secondHandler = new TestEventHandler();
        var sut = new EventDispatcher();
        sut.registerHandler(TestEvent.class, firstHandler);

        // Assert
        assertThatExceptionOfType(TooManyEventHandlersException.class)
                .isThrownBy(() -> sut.registerHandler(TestEvent.class, secondHandler));
    }

    @Test
    void notRegisteredEventCanNotBeDispatched() {
        // Arrange
        var event = new TestEvent();
        var sut = new EventDispatcher();

        // Assert
        assertThatExceptionOfType(EventHandlerNotFoundException.class)
                .isThrownBy(() -> sut.dispatch(event));
    }

    @Test
    void eventIsPassedToRegisteredHandler() {
        // Arrange
        var event = new TestEvent();
        var handler = new TestEventHandler();
        var sut = new EventDispatcher();
        sut.registerHandler(TestEvent.class, handler);

        // Act
        sut.dispatch(event);

        // Assert
        assertThat(handler.getHandleCount()).isEqualTo(1);
    }

    private static class TestEvent extends BaseEvent { }

    @Getter
    private static class TestEventHandler implements EventHandler<TestEvent> {
        private int handleCount = 0;

        @Override
        public void handle(TestEvent event) {
            handleCount += 1;
        }
    }
}
