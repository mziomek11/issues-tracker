package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserNotificationDto extends NotificationDto {
    private Set<UUID> users;

    public UserNotificationDto(String name, Map<String, ? extends Object> data, Set<UUID> users) {
        super(name, data);
        this.users = users;
    }
}
