package com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification;

import com.mateuszziomek.issuestracker.shared.ui.notification.UserNotification;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.exception.NotificationServiceUnavailableException;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class NotificationRestClientV1 implements NotificationRestClient {
    private static final String SERVICE_PATH = "/api/v1/notification-management";
    private static final String NOTIFY_USERS_PATH = "/notifications/users";

    private final DiscoveryClient discoveryClient;
    private final String serviceName;

    /**
     * @throws NotificationServiceUnavailableException see {@link NotificationRestClient#notifyUsers(UserNotification)}
     */
    @Override
    public Mono<Void> notifyUsers(UserNotification notification) {
        return notificationClient()
                .post()
                .uri(NOTIFY_USERS_PATH)
                .bodyValue(notification)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ROLE, UserRole.SYSTEM.toString())
                .retrieve()
                .toBodilessEntity()
                .then();
    }

    /**
     * @throws NotificationServiceUnavailableException see {@link NotificationRestClient#notifyUsers(UserNotification)}
     */
    private WebClient notificationClient() {
        var services = discoveryClient.getInstances(serviceName);

        if (services == null || services.isEmpty()) {
            throw new NotificationServiceUnavailableException();
        }

        var serviceIndex = ThreadLocalRandom.current().nextInt(services.size()) % services.size();
        var service = services.get(serviceIndex);
        var url = service.getUri() + SERVICE_PATH;

        return WebClient.create(url);
    }
}
