package org.example.issuestracker.organizations.query.readmodel.detailsorganization;

import lombok.RequiredArgsConstructor;
import org.example.issuestracker.organizations.query.domain.OrganizationRepository;
import org.example.issuestracker.shared.readmodel.DetailsOrganization;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DetailsOrganizationFinder {
    private final OrganizationRepository organizationRepository;

    public Optional<DetailsOrganization> findById(UUID id) {
        var optionalOrganization = organizationRepository.findById(id);

        if (optionalOrganization.isEmpty()) {
            return Optional.empty();
        }

        var detailsOrganization = DetailsOrganizationMapper.fromModel(optionalOrganization.get());

        return Optional.of(detailsOrganization);
    }
}
