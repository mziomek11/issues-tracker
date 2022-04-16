package org.example.issuestracker.accounts.command.application.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.accounts.command.domain.account.AccountActivationToken;
import org.example.issuestracker.accounts.command.domain.account.AccountId;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ActivateAccountCommand {
    private final AccountId accountId;
    private final AccountActivationToken accountActivationToken;

    public static class ActivateAccountCommandBuilder
            extends CommandBuilder<ActivateAccountCommandBuilder, ActivateAccountCommand> {
        public static final String ACCOUNT_ACTIVATION_TOKEN_FIELD_NAME = "accountActivationToken";

        @NotNull
        private UUID accountId;

        @NotNull
        private UUID accountActivationToken;

        public ActivateAccountCommandBuilder accountId(UUID accountId) {
            this.accountId = accountId;
            return this;
        }

        public ActivateAccountCommandBuilder accountActivationToken(UUID accountActivationToken) {
            this.accountActivationToken = accountActivationToken;
            return this;
        }

        @Override
        protected ActivateAccountCommand create() {
            return new ActivateAccountCommand(
                    new AccountId(accountId),
                    new AccountActivationToken(accountActivationToken)
            );
        }
    }
}
