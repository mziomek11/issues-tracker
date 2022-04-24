package com.mateuszziomek.issuestracker.organizations.query.infrastructure.domain;

import com.mateuszziomek.issuestracker.organizations.query.domain.Organization;
import com.mateuszziomek.issuestracker.organizations.query.domain.OrganizationRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoOrganizationRepository extends MongoRepository<Organization, UUID>, OrganizationRepository { }