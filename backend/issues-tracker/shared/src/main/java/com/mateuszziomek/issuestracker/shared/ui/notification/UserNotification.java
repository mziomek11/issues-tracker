package com.mateuszziomek.issuestracker.shared.ui.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserNotification extends Notification {
    private Set<UUID> users;

    public UserNotification(String name, Map<String, ? extends Object> data, Set<UUID> users) {
        super(name, data);
        this.users = users;
    }
}
