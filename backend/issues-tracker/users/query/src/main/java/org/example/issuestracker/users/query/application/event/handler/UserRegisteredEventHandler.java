package org.example.issuestracker.users.query.application.event.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.EventHandler;
import org.example.issuestracker.users.query.domain.User;
import org.example.issuestracker.users.query.domain.UserRepository;
import org.example.issuestracker.shared.domain.event.UserRegisteredEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventHandler implements EventHandler<UserRegisteredEvent> {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void handle(UserRegisteredEvent event) {
        if (userRepository.findByEmail(event.getUserEmail()).isPresent()) {
            return;
        }

        var user = User.register(event);

        userRepository.save(user);
    }
}