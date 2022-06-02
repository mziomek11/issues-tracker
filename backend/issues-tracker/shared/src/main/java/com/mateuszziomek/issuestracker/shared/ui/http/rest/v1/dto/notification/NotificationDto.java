package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NotificationDto {
    private String name;
    private Map<String, ? extends Object> data;
}
