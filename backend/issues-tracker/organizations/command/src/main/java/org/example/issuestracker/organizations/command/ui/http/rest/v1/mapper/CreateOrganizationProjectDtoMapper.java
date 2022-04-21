package org.example.issuestracker.organizations.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand;
import org.example.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand.CreateOrganizationProjectCommandBuilder;
import org.example.issuestracker.organizations.command.ui.http.rest.v1.dto.CreateOrganizationDto;
import org.example.issuestracker.organizations.command.ui.http.rest.v1.dto.CreateOrganizationProjectDto;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

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
