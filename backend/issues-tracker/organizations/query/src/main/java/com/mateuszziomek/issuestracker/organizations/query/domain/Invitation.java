package com.mateuszziomek.issuestracker.organizations.query.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Invitation {
    @Id
    private UUID organizationId;
    private String organizationName;
    private UUID memberId;
}
