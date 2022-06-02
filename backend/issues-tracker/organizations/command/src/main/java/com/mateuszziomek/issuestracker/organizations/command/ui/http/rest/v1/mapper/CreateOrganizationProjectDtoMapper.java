package com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.organization.CreateOrganizationProjectDto;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand.CreateOrganizationProjectCommandBuilder;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationErrorsMapper;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CreateOrganizationProjectDtoMapper {
    private CreateOrganizationProjectDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static CreateOrganizationProjectCommand toCommand(
            UUID organizationId,
            UUID projectId,
            UUID userId,
            CreateOrganizationProjectDto dto
    ) {
        var builder = CreateOrganizationProjectCommand
                .builder()
                .organizationId(organizationId)
                .organizationOwnerId(userId)
                .projectId(projectId)
                .projectName(dto.name());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<CreateOrganizationProjectCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                CreateOrganizationProjectCommandBuilder.PROJECT_NAME_FIELD_NAME,
                CreateOrganizationProjectDto.PROJECT_NAME_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
