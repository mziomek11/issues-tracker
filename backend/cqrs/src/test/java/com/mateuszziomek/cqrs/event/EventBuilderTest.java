package com.mateuszziomek.cqrs.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class EventBuilderTest {
    @Test
    void throwsEventValidationExceptionWhenBuilderDataIsNotValid() {
        // Arrange
        var builder = new TestEventBuilder();
        builder.name(null);

        // Assert
        assertThatExceptionOfType(EventValidationException.class).isThrownBy(builder::build);
    }

    @Test
    void buildsEventWhenBuilderDataIsValid() {
        // Arrange
        var builder = new TestEventBuilder();
        builder.name("Example name");

        // Act
        var event = builder.build();

        // Assert
        assertThat(event.getName()).isEqualTo("Example name");
    }

    @RequiredArgsConstructor
    @Getter
    private static class TestEvent extends BaseEvent {
        private final String name;
    }

    private static class TestEventBuilder extends EventBuilder<TestEventBuilder, TestEvent> {
        @NotNull
        private String name;

        public TestEventBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        protected TestEvent create() {
            return new TestEvent(name);
        }
    }
}
