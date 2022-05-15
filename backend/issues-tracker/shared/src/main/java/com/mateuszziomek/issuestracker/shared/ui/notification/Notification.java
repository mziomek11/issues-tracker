package com.mateuszziomek.issuestracker.shared.ui.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notification {
    private String name;
    private Map<String, ? extends Object> data;
}
