package org.example.issuestracker.organizations.command.infrastructure.gateway;

import org.example.issuestracker.organizations.command.application.gateway.member.MemberGateway;
import org.example.issuestracker.organizations.command.application.gateway.member.exception.MemberNotFoundException;
import org.example.issuestracker.organizations.command.domain.member.MemberEmail;
import org.example.issuestracker.organizations.command.domain.member.MemberId;
import org.example.issuestracker.shared.domain.valueobject.UserStatus;
import org.example.issuestracker.shared.readmodel.ListUser;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

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
