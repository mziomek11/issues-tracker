package com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.AbstractRestClient;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.exception.UserServiceUnavailableException;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;
import com.mateuszziomek.issuestracker.shared.ui.user.http.rest.v1.param.GetListUsersParam;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;

public class ReactiveUserRestClientV1 extends AbstractRestClient implements ReactiveUserRestClient {
    private static final String SERVICE_PATH = "/api/v1/user-management";
    private static final String GET_USERS_PATH = "/users";

    public ReactiveUserRestClientV1(DiscoveryClient discoveryClient, String serviceName) {
        super(discoveryClient, serviceName, SERVICE_PATH);
    }

    /**
     * @throws UserServiceUnavailableException see {@link ReactiveUserRestClient#getUsers()}
     */
    @Override
    public Flux<ListUser> getUsers() {
        return getUsers(GetListUsersParam.builder().build());
    }

    /**
     * @throws UserServiceUnavailableException see {@link ReactiveUserRestClient#getUsers(GetListUsersParam)}
     */
    @Override
    public Flux<ListUser> getUsers(GetListUsersParam param) {
        return createClient()
                .get()
                .uri(uriBuilder -> buildGetUsersURI(uriBuilder, param))
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ROLE, UserRole.SYSTEM.toString())
                .retrieve()
                .bodyToFlux(ListUser.class);
    }

    private URI buildGetUsersURI(UriBuilder uriBuilder, GetListUsersParam param) {
        if (param.getEmail() != null) {
            uriBuilder.queryParam("email", param.getEmail());
        }

        if (param.getStatus() != null) {
            uriBuilder.queryParam("status", param.getStatus());
        }

        return uriBuilder.path(GET_USERS_PATH).build();
    }

    @Override
    protected IllegalStateException createServiceUnavailableException() {
        return new UserServiceUnavailableException();
    }
}
