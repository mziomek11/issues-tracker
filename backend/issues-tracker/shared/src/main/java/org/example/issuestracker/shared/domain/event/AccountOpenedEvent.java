package org.example.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class AccountOpenedEvent extends BaseEvent {
    private String accountEmail;
    private String accountHashedPassword;
    private String accountActivationToken;

    public static AccountOpenedEvent.AccountOpenedEventBuilder builder() {
        return new AccountOpenedEvent.AccountOpenedEventBuilder();
    }

    private AccountOpenedEvent(
            String accountId,
            String accountEmail,
            String accountHashedPassword,
            String accountActivationToken
    ) {
        super(accountId);

        this.accountEmail = accountEmail;
        this.accountHashedPassword = accountHashedPassword;
        this.accountActivationToken = accountActivationToken;
    }

    public static class AccountOpenedEventBuilder
            extends EventBuilder<AccountOpenedEvent.AccountOpenedEventBuilder, AccountOpenedEvent> {
        @NotNull
        private UUID accountId;

        @NotBlank
        private String accountEmail;

        @NotBlank
        private String accountHashedPassword;

        @NotBlank
        private UUID accountActivationToken;

        public AccountOpenedEvent.AccountOpenedEventBuilder accountId(UUID accountId) {
            this.accountId = accountId;
            return this;
        }

        public AccountOpenedEvent.AccountOpenedEventBuilder accountEmail(String accountEmail) {
            this.accountEmail = accountEmail;
            return this;
        }

        public AccountOpenedEvent.AccountOpenedEventBuilder accountHashedPassword(String accountHashedPassword) {
            this.accountHashedPassword = accountHashedPassword;
            return this;
        }

        public AccountOpenedEvent.AccountOpenedEventBuilder accountActivationToken(UUID accountActivationToken) {
            this.accountActivationToken = accountActivationToken;
            return this;
        }

        @Override
        protected AccountOpenedEvent create() {
            return new AccountOpenedEvent(
                    accountId.toString(),
                    accountEmail,
                    accountHashedPassword,
                    accountActivationToken.toString()
            );
        }
    }
}
