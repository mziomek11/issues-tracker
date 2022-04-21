package org.example.issuestracker.organizations.query.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Project {
    private UUID id;
    private String name;
}
