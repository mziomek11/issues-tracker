package com.mateuszziomek.issuestracker.organizations.query.readmodel.organization.details;

import com.mateuszziomek.issuestracker.organizations.query.domain.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DetailsOrganizationFinder {
    private final OrganizationRepository organizationRepository;

    public Mono<DetailsOrganization> findById(UUID id) {
        return organizationRepository
                .findById(id)
                .map(DetailsOrganizationMapper::fromModel);
    }
}
