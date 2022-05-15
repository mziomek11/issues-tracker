package com.mateuszziomek.issuestracker.notifications.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.shared.ui.notification.UserNotification;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.exception.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notification-management")
public class NotificationRestController {
    private final Sinks.Many<UserNotification> userNotificationSink = Sinks.many().multicast().directBestEffort();

    /**
     * @throws AccessDeniedException if user role is not {@link UserRole#SYSTEM}
     */
    @PostMapping("/notifications/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createNotification(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ROLE) UserRole userRole,
            @RequestBody UserNotification userNotification
    ) {
        if (!UserRole.SYSTEM.equals(userRole)) {
            throw new AccessDeniedException();
        }

        userNotificationSink.tryEmitNext(userNotification);

        return Mono.empty();
    }

    @GetMapping("/notifications/users")
    public Flux<ServerSentEvent> subscribeToUserNotifications(
            @RequestHeader(SecurityHeaders.ISSUES_TRACKER_USER_ID) UUID userId
    ) {

        return userNotificationSink
                .asFlux()
                .filter(notification -> notification.getUsers().contains(userId))
                .map(notification -> ServerSentEvent
                        .builder(notification.getData())
                        .event(notification.getName())
                        .build()
                );
    }
}