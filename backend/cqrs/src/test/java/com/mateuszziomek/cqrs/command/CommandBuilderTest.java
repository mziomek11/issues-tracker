package com.mateuszziomek.cqrs.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.*;

class CommandBuilderTest {
    @Test
    void throwsCommandValidationExceptionWhenBuilderDataIsNotValid() {
        // Arrange
        var builder = new TestCommandBuilder();
        builder.name(null);

        // Assert
        assertThatExceptionOfType(CommandValidationException.class).isThrownBy(builder::build);
    }

    @Test
    void buildsCommandWhenBuilderDataIsValid() {
        // Arrange
        var builder = new TestCommandBuilder();
        builder.name("Example name");

        // Act
        var command = builder.build();

        // Assert
        assertThat(command.getName()).isEqualTo("Example name");
    }

    @RequiredArgsConstructor
    @Getter
    private static class TestCommand {
        private final String name;
    }

    private static class TestCommandBuilder extends CommandBuilder<TestCommandBuilder, TestCommand> {
        @NotNull
        private String name;

        public TestCommandBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        protected TestCommand create() {
            return new TestCommand(name);
        }
    }
}
