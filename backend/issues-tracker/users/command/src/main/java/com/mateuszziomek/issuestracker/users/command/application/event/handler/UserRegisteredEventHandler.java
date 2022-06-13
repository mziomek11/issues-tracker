package com.mateuszziomek.issuestracker.users.command.application.event.handler;

import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import com.mateuszziomek.issuestracker.users.command.projection.User;
import com.mateuszziomek.issuestracker.users.command.projection.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventHandler implements EventHandler<UserRegisteredEvent> {
    private final UserRepository userRepository;

    @Override
    public void handle(UserRegisteredEvent event) {
        if (userRepository.findByEmail(event.getUserEmail()).isPresent()) {
            return;
        }

        userRepository.save(User.register(event));
    }
}
