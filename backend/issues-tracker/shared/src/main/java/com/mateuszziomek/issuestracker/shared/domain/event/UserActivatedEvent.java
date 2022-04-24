package com.mateuszziomek.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class UserActivatedEvent extends BaseEvent {
    public static UserActivatedEventBuilder builder() {
        return new UserActivatedEventBuilder();
    }

    private UserActivatedEvent(UUID userId) {
        super(userId);
    }

    public static class UserActivatedEventBuilder extends EventBuilder<UserActivatedEventBuilder, UserActivatedEvent> {
        @NotNull
        private UUID userId;

        public UserActivatedEventBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        @Override
        protected UserActivatedEvent create() {
            return new UserActivatedEvent(userId);
        }
    }
}
