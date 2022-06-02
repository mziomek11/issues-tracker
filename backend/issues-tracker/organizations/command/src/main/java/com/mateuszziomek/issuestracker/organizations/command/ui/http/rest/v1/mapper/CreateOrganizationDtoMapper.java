package com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationCommand.CreateOrganizationCommandBuilder;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.organization.CreateOrganizationDto;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationErrorsMapper;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CreateOrganizationDtoMapper {
    private CreateOrganizationDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static CreateOrganizationCommand toCommand(UUID organizationId, UUID userId, CreateOrganizationDto dto) {
        var builder = CreateOrganizationCommand
                .builder()
                .organizationId(organizationId)
                .organizationOwnerId(userId)
                .organizationName(dto.name());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<CreateOrganizationCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                CreateOrganizationCommandBuilder.ORGANIZATION_NAME_FIELD_NAME,
                CreateOrganizationDto.ORGANIZATION_NAME_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
