package org.example.issuestracker.issues.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.issues.command.application.command.ChangeIssueTypeCommand;
import org.example.issuestracker.issues.command.application.command.ChangeIssueTypeCommand.ChangeIssueTypeCommandBuilder;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.ChangeIssueTypeDto;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ChangeIssueTypeDtoMapper {
    private ChangeIssueTypeDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static ChangeIssueTypeCommand toCommand(
            UUID issueId,
            UUID organizationId,
            UUID projectId,
            UUID memberId,
            ChangeIssueTypeDto dto
    ) {
        var builder = ChangeIssueTypeCommand
                .builder()
                .issueId(issueId)
                .organizationId(organizationId)
                .projectId(projectId)
                .memberId(memberId)
                .issueType(dto.type());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<ChangeIssueTypeCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                ChangeIssueTypeCommandBuilder.ISSUE_TYPE_FIELD_NAME,
                ChangeIssueTypeDto.ISSUE_TYPE_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
