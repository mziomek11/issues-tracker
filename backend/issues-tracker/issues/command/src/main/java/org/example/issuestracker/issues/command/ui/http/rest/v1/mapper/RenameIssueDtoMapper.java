package org.example.issuestracker.issues.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.issues.command.application.command.RenameIssueCommand;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.RenameIssueDto;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RenameIssueDtoMapper {
    private RenameIssueDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static RenameIssueCommand toCommand(UUID issueId, RenameIssueDto dto) {
        var builder = RenameIssueCommand
                .builder()
                .issueId(issueId)
                .issueName(dto.issueName());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<RenameIssueCommand.RenameIssueCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                RenameIssueCommand.RenameIssueCommandBuilder.ISSUE_NAME_FIELD_NAME,
                RenameIssueDto.ISSUE_NAME_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
