package com.mateuszziomek.issuestracker.organizations.command.infrastructure.gateway;

import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.MemberGateway;
import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception.MemberNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception.MemberServiceUnavailableException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberEmail;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.readmodel.ListUser;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class MemberGatewayImpl implements MemberGateway {
    private final DiscoveryClient discoveryClient;

    /**
     * @throws MemberNotFoundException see {@link MemberGateway#getMemberId(MemberEmail)}
     * @throws MemberServiceUnavailableException see {@link MemberGateway#getMemberId(MemberEmail)}
     */
    @Override
    public MemberId getMemberId(MemberEmail memberEmail) {
        var listUsers = userClient()
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/users")
                                .queryParam("email", memberEmail.text())
                                .queryParam("status", UserStatus.ACTIVATED)
                                .build()
                )
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ROLE, UserRole.SYSTEM.toString())
                .retrieve()
                .bodyToFlux(ListUser.class)
                .collectList()
                .block();

        if (listUsers == null || listUsers.isEmpty() || listUsers.get(0) == null) {
            throw new MemberNotFoundException(memberEmail);
        }

        return new MemberId(listUsers.get(0).getId());
    }

    /**
     * @throws MemberServiceUnavailableException {@link MemberGateway#getMemberId(MemberEmail)}
     */
    public WebClient userClient() {
        var services = discoveryClient.getInstances(System.getenv("SERVICE_USERS_QUERY_NAME"));

        if (services == null || services.isEmpty()) {
            throw new MemberServiceUnavailableException();
        }

        var serviceIndex = ThreadLocalRandom.current().nextInt(services.size()) % services.size();
        var service = services.get(serviceIndex);
        var url = service.getUri() + "/api/v1/user-management";

        return WebClient.create(url);
    }
}
