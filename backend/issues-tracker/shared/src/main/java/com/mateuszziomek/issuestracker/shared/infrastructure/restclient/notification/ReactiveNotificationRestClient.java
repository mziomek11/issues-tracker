package com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification;

import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.notification.UserNotificationDto;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.exception.NotificationServiceUnavailableException;
import reactor.core.publisher.Mono;

public interface ReactiveNotificationRestClient {
    /**
     * @throws NotificationServiceUnavailableException if notification service in unavailable
     */
    Mono<Void> notifyUsers(UserNotificationDto notification);
}
