package com.mateuszziomek.issuestracker.organizations.query.readmodel.detailsorganization;

import com.mateuszziomek.issuestracker.organizations.query.domain.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.shared.readmodel.DetailsOrganization;
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
