package org.example.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class UserRegisteredEvent extends BaseEvent {
    private String userEmail;
    private String userHashedPassword;
    private String userActivationToken;

    public static UserRegisteredEventBuilder builder() {
        return new UserRegisteredEventBuilder();
    }

    private UserRegisteredEvent(
            String userId,
            String userEmail,
            String userHashedPassword,
            String userActivationToken
    ) {
        super(userId);

        this.userEmail = userEmail;
        this.userHashedPassword = userHashedPassword;
        this.userActivationToken = userActivationToken;
    }

    public static class UserRegisteredEventBuilder extends EventBuilder<UserRegisteredEventBuilder, UserRegisteredEvent> {
        @NotNull
        private UUID userId;

        @NotBlank
        @Email
        private String userEmail;

        @NotBlank
        private String userHashedPassword;

        @NotNull
        private UUID userActivationToken;

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

        public UserRegisteredEventBuilder userActivationToken(UUID userActivationToken) {
            this.userActivationToken = userActivationToken;
            return this;
        }

        @Override
        protected UserRegisteredEvent create() {
            return new UserRegisteredEvent(
                    userId.toString(),
                    userEmail,
                    userHashedPassword,
                    userActivationToken.toString()
            );
        }
    }
}
