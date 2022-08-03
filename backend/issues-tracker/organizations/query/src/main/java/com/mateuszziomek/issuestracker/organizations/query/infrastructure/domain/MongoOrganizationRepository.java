package com.mateuszziomek.issuestracker.organizations.query.infrastructure.domain;

import com.mateuszziomek.issuestracker.organizations.query.domain.organization.Organization;
import com.mateuszziomek.issuestracker.organizations.query.domain.organization.OrganizationRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoOrganizationRepository extends ReactiveMongoRepository<Organization, UUID>, OrganizationRepository { }