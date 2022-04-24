package com.mateuszziomek.issuestracker.users.command.infrastructure.gateway;

import com.mateuszziomek.issuestracker.shared.readmodel.ListUser;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.UserGateway;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserEmailNotAvailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserGatewayImpl implements UserGateway {
    private final WebClient userClient = WebClient.create("http://localhost:8085/api/v1/user-management");
    /**
     * @throws UserEmailNotAvailableException see {@link UserGateway#ensureUserEmailIsAvailable(UserEmail)}
     */
    @Override
    public void ensureUserEmailIsAvailable(UserEmail userEmail) {
        var listUsers = userClient
                .get()
                .uri(uriBuilder ->
                    uriBuilder
                        .path("/users")
                        .queryParam("email", userEmail.text())
                        .build()
                )
                .retrieve()
                .bodyToFlux(ListUser.class)
                .collectList()
                .block();

        if (listUsers != null && !listUsers.isEmpty()) {
            throw new UserEmailNotAvailableException(userEmail);
        }
    }
}
