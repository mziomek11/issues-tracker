package org.example.issuestracker.users.command.application.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.users.command.domain.account.UserEmail;
import org.example.issuestracker.users.command.domain.account.UserId;
import org.example.issuestracker.users.command.domain.account.UserPlainPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class RegisterUserCommand {
    private final UserId userId;
    private final UserEmail userEmail;
    private final UserPlainPassword userPlainPassword;

    public static RegisterUserCommandBuilder builder() {
        return new RegisterUserCommandBuilder();
    }

    public static class RegisterUserCommandBuilder extends CommandBuilder<RegisterUserCommandBuilder, RegisterUserCommand> {
        public static final String USER_EMAIL_FIELD_NAME = "userEmail";
        public static final String USER_PLAIN_PASSWORD_FIELD_NAME = "userPlainPassword";

        @NotNull
        private UUID userId;

        @NotBlank
        @Email
        private String userEmail;

        @NotBlank
        private String userPlainPassword;

        public RegisterUserCommandBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public RegisterUserCommandBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public RegisterUserCommandBuilder userPlainPassword(String userPlainPassword) {
            this.userPlainPassword = userPlainPassword;
            return this;
        }

        @Override
        protected RegisterUserCommand create() {
            return new RegisterUserCommand(
                    new UserId(userId),
                    new UserEmail(userEmail),
                    new UserPlainPassword(userPlainPassword)
            );
        }
    }
}
