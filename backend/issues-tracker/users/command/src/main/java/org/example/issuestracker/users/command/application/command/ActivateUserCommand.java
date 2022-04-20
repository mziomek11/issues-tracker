package org.example.issuestracker.users.command.application.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.users.command.domain.user.UserActivationToken;
import org.example.issuestracker.users.command.domain.user.UserId;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ActivateUserCommand {
    private final UserId userId;
    private final UserActivationToken userActivationToken;

    public static ActivateUserCommandBuilder builder() {
        return new ActivateUserCommandBuilder();
    }

    public static class ActivateUserCommandBuilder
            extends CommandBuilder<ActivateUserCommandBuilder, ActivateUserCommand> {
        public static final String USER_ACTIVATION_TOKEN_FIELD_NAME = "userActivationToken";

        @NotNull
        private UUID userId;

        @NotNull
        private UUID userActivationToken;

        public ActivateUserCommandBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public ActivateUserCommandBuilder userActivationToken(UUID userActivationToken) {
            this.userActivationToken = userActivationToken;
            return this;
        }

        @Override
        protected ActivateUserCommand create() {
            return new ActivateUserCommand(
                    new UserId(userId),
                    new UserActivationToken(userActivationToken)
            );
        }
    }
}
