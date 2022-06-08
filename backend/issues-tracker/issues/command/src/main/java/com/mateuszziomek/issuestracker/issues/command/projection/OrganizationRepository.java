package com.mateuszziomek.issuestracker.issues.command.projection;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrganizationRepository extends CrudRepository<Organization, UUID> { }
