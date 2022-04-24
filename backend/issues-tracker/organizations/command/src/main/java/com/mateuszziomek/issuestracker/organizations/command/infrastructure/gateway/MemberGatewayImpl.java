package com.mateuszziomek.issuestracker.organizations.command.infrastructure.gateway;

import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.MemberGateway;
import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception.MemberNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberEmail;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;
import com.mateuszziomek.issuestracker.shared.readmodel.ListUser;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MemberGatewayImpl implements MemberGateway {
    private final WebClient userClient = WebClient.create("http://localhost:8085/api/v1/user-management");

    /**
     * @throws MemberNotFoundException see {@link MemberGateway#getMemberId(MemberEmail)}
     */
    @Override
    public MemberId getMemberId(MemberEmail memberEmail) {
        var listUsers = userClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/users")
                                .queryParam("email", memberEmail.text())
                                .queryParam("status", UserStatus.ACTIVATED)
                                .build()
                )
                .retrieve()
                .bodyToFlux(ListUser.class)
                .collectList()
                .block();

        if (listUsers == null || listUsers.isEmpty() || listUsers.get(0) == null) {
            throw new MemberNotFoundException(memberEmail);
        }

        return new MemberId(listUsers.get(0).getId());
    }
}
