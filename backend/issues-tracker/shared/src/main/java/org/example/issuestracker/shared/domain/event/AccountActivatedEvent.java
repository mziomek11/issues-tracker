package org.example.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class AccountActivatedEvent extends BaseEvent {
    public static AccountActivatedEvent.AccountActivatedEventBuilder builder() {
        return new AccountActivatedEvent.AccountActivatedEventBuilder();
    }

    private AccountActivatedEvent(String accountId) {
        super(accountId);
    }

    public static class AccountActivatedEventBuilder
            extends EventBuilder<AccountActivatedEvent.AccountActivatedEventBuilder, AccountActivatedEvent> {
        @NotNull
        private UUID accountId;

        public AccountActivatedEvent.AccountActivatedEventBuilder accountId(UUID accountId) {
            this.accountId = accountId;
            return this;
        }

        @Override
        protected AccountActivatedEvent create() {
            return new AccountActivatedEvent(accountId.toString());
        }
    }
}
