package com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification;

import com.mateuszziomek.issuestracker.shared.ui.notification.UserNotification;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.exception.NotificationServiceUnavailableException;
import reactor.core.publisher.Mono;

public interface ReactiveNotificationRestClient {
    /**
     * @throws NotificationServiceUnavailableException if notification service in unavailable
     */
    Mono<Void> notifyUsers(UserNotification notification);
}
