package com.mateuszziomek.issuestracker.users.query.application.event.handler;

import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.issuestracker.shared.domain.event.UserActivatedEvent;
import com.mateuszziomek.issuestracker.users.query.domain.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserActivatedEventHandler implements EventHandler<UserActivatedEvent> {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void handle(UserActivatedEvent event) {
        var optionalUser = userRepository.findById(event.getId());
        if (optionalUser.isEmpty()) {
            return;
        }

        optionalUser.get().activate();
    }
}
