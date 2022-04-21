package org.example.issuestracker.organizations.query.domain;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Organization {
    private UUID id;
    private UUID ownerId;
    private String name;
    private List<Project> projects;
}
