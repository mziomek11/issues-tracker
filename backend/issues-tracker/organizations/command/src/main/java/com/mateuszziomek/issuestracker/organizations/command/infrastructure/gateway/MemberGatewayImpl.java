package com.mateuszziomek.issuestracker.organizations.command.infrastructure.gateway;

import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.MemberGateway;
import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception.MemberNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception.MemberServiceUnavailableException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberEmail;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.ReactiveUserRestClient;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.exception.UserServiceUnavailableException;
import com.mateuszziomek.issuestracker.shared.readmodel.user.ListUser;
import com.mateuszziomek.issuestracker.shared.ui.user.http.rest.v1.param.GetListUsersParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberGatewayImpl implements MemberGateway {
    private final ReactiveUserRestClient userRestClient;

    /**
     * @throws MemberNotFoundException see {@link MemberGateway#getMemberId(MemberEmail)}
     * @throws MemberServiceUnavailableException see {@link MemberGateway#getMemberId(MemberEmail)}
     */
    @Override
    public MemberId getMemberId(MemberEmail memberEmail) {
        var queryParams = GetListUsersParam
                .builder()
                .email(memberEmail.text())
                .status(UserStatus.ACTIVATED)
                .build();

        List<ListUser> listUsers;

        try {
            listUsers = userRestClient
                    .getUsers(queryParams)
                    .collectList()
                    .block();
        } catch (UserServiceUnavailableException exception) {
            throw new MemberServiceUnavailableException();
        }

        if (listUsers == null || listUsers.isEmpty() || listUsers.get(0) == null) {
            throw new MemberNotFoundException(memberEmail);
        }

        return new MemberId(listUsers.get(0).getId());
    }
}
