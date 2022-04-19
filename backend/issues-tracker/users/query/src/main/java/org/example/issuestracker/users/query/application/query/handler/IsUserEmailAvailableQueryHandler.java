package org.example.issuestracker.users.query.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.query.QueryHandler;
import org.example.issuestracker.users.query.application.query.IsUserEmailAvailableQuery;
import org.example.issuestracker.users.query.domain.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IsUserEmailAvailableQueryHandler implements QueryHandler<IsUserEmailAvailableQuery, Boolean> {
    private final UserRepository userRepository;

    @Override
    public Boolean handle(IsUserEmailAvailableQuery query) {
        return userRepository.findByEmail(query.getUserEmail()).isPresent();
    }
}
