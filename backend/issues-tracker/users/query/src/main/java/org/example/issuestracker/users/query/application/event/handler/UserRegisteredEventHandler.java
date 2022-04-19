package org.example.issuestracker.users.query.application.event.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.EventHandler;
import org.example.issuestracker.users.query.domain.User;
import org.example.issuestracker.users.query.domain.UserRepository;
import org.example.issuestracker.shared.domain.event.UserRegisteredEvent;
import org.example.issuestracker.shared.domain.valueobject.UserStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventHandler implements EventHandler<UserRegisteredEvent> {
    private final UserRepository userRepository;

    @Override
    public void handle(UserRegisteredEvent event) {
        if (userRepository.findByEmail(event.getUserEmail()).isPresent()) {
            return;
        }

        var account = User
                .builder()
                .id(UUID.fromString(event.getId()))
                .password(event.getUserHashedPassword())
                .email(event.getUserEmail())
                .status(UserStatus.UNVERIFIED)
                .build();

        userRepository.save(account);
    }
}
