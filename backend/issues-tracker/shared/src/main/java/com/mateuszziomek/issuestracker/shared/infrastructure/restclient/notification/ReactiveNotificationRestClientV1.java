package com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification;

import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.AbstractRestClient;
import com.mateuszziomek.issuestracker.shared.ui.notification.UserNotification;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.exception.NotificationServiceUnavailableException;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import reactor.core.publisher.Mono;

public class ReactiveNotificationRestClientV1 extends AbstractRestClient implements ReactiveNotificationRestClient {
    private static final String SERVICE_PATH = "/api/v1/notification-management";
    private static final String NOTIFY_USERS_PATH = "/notifications/users";

    public ReactiveNotificationRestClientV1(DiscoveryClient discoveryClient, String serviceName) {
        super(discoveryClient, serviceName, SERVICE_PATH);
    }

    /**
     * @throws NotificationServiceUnavailableException see {@link ReactiveNotificationRestClient#notifyUsers(UserNotification)}
     */
    @Override
    public Mono<Void> notifyUsers(UserNotification notification) {
        return createClient()
                .post()
                .uri(NOTIFY_USERS_PATH)
                .bodyValue(notification)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ROLE, UserRole.SYSTEM.toString())
                .retrieve()
                .toBodilessEntity()
                .then();
    }

    @Override
    protected IllegalStateException createServiceUnavailableException() {
        return new NotificationServiceUnavailableException();
    }
}
