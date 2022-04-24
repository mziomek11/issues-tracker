package com.mateuszziomek.issuestracker.organizations.query.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Member {
    private UUID id;
}
