package com.mateuszziomek.issuestracker.shared.readmodel;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ListUser {
    private final UUID id;
    private final String email;
}
