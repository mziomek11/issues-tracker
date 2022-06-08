package com.mateuszziomek.issuestracker.issues.command.infrastructure.projection;

import com.mateuszziomek.issuestracker.issues.command.projection.Organization;
import com.mateuszziomek.issuestracker.issues.command.projection.OrganizationRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoOrganizationRepository extends OrganizationRepository, MongoRepository<Organization, UUID> { }
