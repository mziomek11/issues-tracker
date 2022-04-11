package org.example.issuestracker.issues.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.issues.command.application.command.ChangeIssueContentCommand;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.ChangeIssueContentDto;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ChangeIssueContentDtoMapper {
    private ChangeIssueContentDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static ChangeIssueContentCommand toCommand(UUID issueId, ChangeIssueContentDto dto) {
        var builder = ChangeIssueContentCommand
                .builder()
                .issueId(issueId)
                .issueContent(dto.issueContent());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<ChangeIssueContentCommand.ChangeIssueContentCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                ChangeIssueContentCommand.ChangeIssueContentCommandBuilder.ISSUE_CONTENT_FIELD_NAME,
                ChangeIssueContentDto.ISSUE_CONTENT_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
