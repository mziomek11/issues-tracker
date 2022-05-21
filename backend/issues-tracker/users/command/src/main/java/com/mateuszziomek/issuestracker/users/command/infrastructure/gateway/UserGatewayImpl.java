package com.mateuszziomek.issuestracker.users.command.infrastructure.gateway;

import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.ReactiveUserRestClient;
import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;
import com.mateuszziomek.issuestracker.shared.ui.user.http.rest.v1.param.GetListUsersParam;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.UserGateway;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserEmailUnavailableException;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserServiceUnavailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {
    private final ReactiveUserRestClient userRestClient;

    /**
     * @throws UserEmailUnavailableException see {@link UserGateway#ensureUserEmailIsAvailable(UserEmail)}
     * @throws UserServiceUnavailableException see {@link UserGateway#ensureUserEmailIsAvailable(UserEmail)}
     */
    @Override
    public void ensureUserEmailIsAvailable(UserEmail userEmail) {
        var queryParams = GetListUsersParam
                .builder()
                .email(userEmail.text())
                .build();

        List<ListUser> listUsers;

        try {
            listUsers = userRestClient
                    .getUsers(queryParams)
                    .collectList()
                    .block();
        } catch (com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.exception.UserServiceUnavailableException ex) {
            throw new UserServiceUnavailableException();
        }

        if (listUsers != null && !listUsers.isEmpty()) {
            throw new UserEmailUnavailableException(userEmail);
        }
    }
}
