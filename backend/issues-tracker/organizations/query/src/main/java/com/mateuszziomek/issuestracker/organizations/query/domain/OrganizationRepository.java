package com.mateuszziomek.issuestracker.organizations.query.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface OrganizationRepository extends ReactiveCrudRepository<Organization, UUID> { }
