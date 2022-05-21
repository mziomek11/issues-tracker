package com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user;

import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.exception.UserServiceUnavailableException;
import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;
import com.mateuszziomek.issuestracker.shared.ui.user.http.rest.v1.param.GetListUsersParam;
import reactor.core.publisher.Flux;

public interface ReactiveUserRestClient {
    /**
     * @throws UserServiceUnavailableException if user service is unavailable
     */
    Flux<ListUser> getUsers();
    /**
     * @throws UserServiceUnavailableException if user service is unavailable
     */
    Flux<ListUser> getUsers(GetListUsersParam param);
}
