package com.mateuszziomek.cqrs.event.dispatcher;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ReactiveEventDispatcherTest {
    @Test
    void eventCanHaveOnlyOneHandler() {
        // Arrange
        var firstHandler = new TestEventHandler();
        var secondHandler = new TestEventHandler();
        var sut = new ReactiveEventDispatcher();
        sut.registerHandler(TestEvent.class, firstHandler);

        // Assert
        assertThatExceptionOfType(TooManyEventHandlersException.class)
                .isThrownBy(() -> sut.registerHandler(TestEvent.class, secondHandler));
    }

    @Test
    void notRegisteredEventCanNotBeDispatched() {
        // Arrange
        var event = new TestEvent();
        var sut = new ReactiveEventDispatcher();

        // Assert
        assertThatExceptionOfType(EventHandlerNotFoundException.class)
                .isThrownBy(() -> sut.dispatch(event));
    }

    @Test
    void eventIsPassedToRegisteredHandler() {
        // Arrange
        var event = new TestEvent();
        var handler = new TestEventHandler();
        var sut = new ReactiveEventDispatcher();
        sut.registerHandler(TestEvent.class, handler);

        // Act
        sut.dispatch(event).block();

        // Assert
        assertThat(handler.getHandleCount()).isEqualTo(1);
    }

    private static class TestEvent extends BaseEvent { }

    @Getter
    private static class TestEventHandler implements ReactiveEventHandler<TestEvent> {
        private int handleCount = 0;

        @Override
        public Mono<Void> handle(TestEvent event) {
            handleCount += 1;
            return Mono.empty().then();
        }
    }
}
