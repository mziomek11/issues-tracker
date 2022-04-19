package org.example.issuestracker.users.command.infrastructure.gateway;

import org.example.issuestracker.users.command.application.gateway.UserGateway;
import org.example.issuestracker.users.command.application.gateway.exception.UserEmailNotAvailableException;
import org.example.issuestracker.users.command.domain.account.UserEmail;
import org.springframework.stereotype.Service;

@Service
public class UserGatewayImpl implements UserGateway {
    /**
     * @throws UserEmailNotAvailableException see {@link UserGateway#ensureUserEmailIsAvailable(UserEmail)}
     */
    @Override
    public void ensureUserEmailIsAvailable(UserEmail userEmail) {
        // @TODO implement
    }
}
