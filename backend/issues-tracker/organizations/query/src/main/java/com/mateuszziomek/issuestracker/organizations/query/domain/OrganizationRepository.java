package com.mateuszziomek.issuestracker.organizations.query.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, UUID> { }
