package org.example.issuestracker.organizations.command.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.example.issuestracker.organizations.command.ui.http.rest.v1.dto.CreateOrganizationDto;
import org.example.issuestracker.organizations.command.ui.http.rest.v1.mapper.CreateOrganizationDtoMapper;
import org.example.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organization-management")
@RequiredArgsConstructor
public class OrganizationRestController {
    private final CommandDispatcher commandDispatcher;

    /**
     * @throws RestValidationException see {@link CreateOrganizationDtoMapper#toCommand(UUID, UUID, CreateOrganizationDto)}
     */
    @PostMapping("/organizations")
    public ResponseEntity<UUID> createOrganization(@RequestBody CreateOrganizationDto createOrganizationDto) {
        var organizationId = UUID.randomUUID();
        // @TODO pass user id from header
        var registerUserCommand = CreateOrganizationDtoMapper.toCommand(
                organizationId,
                UUID.randomUUID(),
                createOrganizationDto
        );

        commandDispatcher.dispatch(registerUserCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(organizationId);
    }
}
