package org.example.issuestracker.users.command.infrastructure.gateway;

import org.example.issuestracker.users.command.application.gateway.UserGateway;
import org.example.issuestracker.users.command.application.gateway.exception.UserEmailNotAvailableException;
import org.example.issuestracker.users.command.domain.account.UserEmail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserGatewayImpl implements UserGateway {
    private final WebClient client = WebClient.create("http://localhost:8085/api/v1/user-management");
    /**
     * @throws UserEmailNotAvailableException see {@link UserGateway#ensureUserEmailIsAvailable(UserEmail)}
     */
    @Override
    public void ensureUserEmailIsAvailable(UserEmail userEmail) {
        var isUserEmailAvailable = client
                .get()
                .uri("/users-by-email/{email}", userEmail.text())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.FALSE.equals(isUserEmailAvailable)) {
            throw new UserEmailNotAvailableException(userEmail);
        }
    }
}
