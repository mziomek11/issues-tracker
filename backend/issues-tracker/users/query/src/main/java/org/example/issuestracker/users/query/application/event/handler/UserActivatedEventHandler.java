package org.example.issuestracker.users.query.application.event.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.EventHandler;
import org.example.issuestracker.shared.domain.event.UserActivatedEvent;
import org.example.issuestracker.users.query.domain.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserActivatedEventHandler implements EventHandler<UserActivatedEvent> {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void handle(UserActivatedEvent event) {
        var optionalUser = userRepository.findById(UUID.fromString(event.getId()));
        if (optionalUser.isEmpty()) {
            return;
        }

        optionalUser.get().activate();
    }
}
