package org.example.issuestracker.accounts.command.application.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.accounts.command.domain.account.AccountEmail;
import org.example.issuestracker.accounts.command.domain.account.AccountId;
import org.example.issuestracker.accounts.command.domain.account.AccountPlainPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class OpenAccountCommand {
    private final AccountId accountId;
    private final AccountEmail accountEmail;
    private final AccountPlainPassword accountPlainPassword;

    public static OpenAccountCommandBuilder builder() {
        return new OpenAccountCommandBuilder();
    }

    public static class OpenAccountCommandBuilder extends CommandBuilder<OpenAccountCommandBuilder, OpenAccountCommand> {
        public static final String ACCOUNT_EMAIL_FIELD_NAME = "accountEmail";
        public static final String ACCOUNT_PLAIN_PASSWORD_FIELD_NAME = "accountPlainPassword";

        @NotNull
        private UUID accountId;

        @NotBlank
        @Email
        private String accountEmail;

        @NotBlank
        private String accountPlainPassword;

        public OpenAccountCommandBuilder accountId(UUID accountId) {
            this.accountId = accountId;
            return this;
        }

        public OpenAccountCommandBuilder accountEmail(String accountEmail) {
            this.accountEmail = accountEmail;
            return this;
        }

        public OpenAccountCommandBuilder accountPlainPassword(String accountPlainPassword) {
            this.accountPlainPassword = accountPlainPassword;
            return this;
        }

        @Override
        protected OpenAccountCommand create() {
            return new OpenAccountCommand(
                    new AccountId(accountId),
                    new AccountEmail(accountEmail),
                    new AccountPlainPassword(accountPlainPassword)
            );
        }
    }
}
