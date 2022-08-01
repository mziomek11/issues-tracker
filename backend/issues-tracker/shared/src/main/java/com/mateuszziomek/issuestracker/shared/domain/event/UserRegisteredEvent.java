package com.mateuszziomek.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class UserRegisteredEvent extends BaseEvent {
    private String userEmail;
    private String userHashedPassword;

    public static UserRegisteredEventBuilder builder() {
        return new UserRegisteredEventBuilder();
    }

    private UserRegisteredEvent(
            UUID userId,
            String userEmail,
            String userHashedPassword
    ) {
        super(userId);

        this.userEmail = userEmail;
        this.userHashedPassword = userHashedPassword;
    }

    public static class UserRegisteredEventBuilder extends EventBuilder<UserRegisteredEventBuilder, UserRegisteredEvent> {
        @NotNull
        private UUID userId;

        @NotBlank
        @Email
        private String userEmail;

        @NotBlank
        private String userHashedPassword;

        public UserRegisteredEventBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public UserRegisteredEventBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public UserRegisteredEventBuilder userHashedPassword(String userHashedPassword) {
            this.userHashedPassword = userHashedPassword;
            return this;
        }

        @Override
        protected UserRegisteredEvent create() {
            return new UserRegisteredEvent(
                    userId,
                    userEmail,
                    userHashedPassword
            );
        }
    }
}
