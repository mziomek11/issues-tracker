package com.mateuszziomek.issuestracker.users.query.application.event.handler;

import com.mateuszziomek.issuestracker.users.query.domain.User;
import com.mateuszziomek.issuestracker.users.query.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
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

        var user = User.register(event);

        userRepository.save(user);
    }
}
