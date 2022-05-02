package com.mateuszziomek.issuestracker.users.command.infrastructure.gateway;

import com.mateuszziomek.issuestracker.shared.readmodel.ListUser;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.UserGateway;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserEmailUnavailableException;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserServiceUnavailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {
    private final DiscoveryClient discoveryClient;

    /**
     * @throws UserEmailUnavailableException see {@link UserGateway#ensureUserEmailIsAvailable(UserEmail)}
     * @throws UserServiceUnavailableException see {@link UserGateway#ensureUserEmailIsAvailable(UserEmail)}
     */
    @Override
    public void ensureUserEmailIsAvailable(UserEmail userEmail) {
       var listUsers = userClient()
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
            throw new UserEmailUnavailableException(userEmail);
        }
    }

    /**
     * @throws UserServiceUnavailableException see {@link UserGateway#ensureUserEmailIsAvailable(UserEmail)}
     */
    public WebClient userClient() {
        var services = discoveryClient.getInstances(System.getenv("SERVICE_USERS_QUERY_NAME"));

        if (services == null || services.isEmpty()) {
            throw new UserServiceUnavailableException();
        }

        var serviceIndex = ThreadLocalRandom.current().nextInt(services.size()) % services.size();
        var service = services.get(serviceIndex);
        var url = service.getUri() + "/api/v1/user-management";

        return WebClient.create(url);
    }
}
