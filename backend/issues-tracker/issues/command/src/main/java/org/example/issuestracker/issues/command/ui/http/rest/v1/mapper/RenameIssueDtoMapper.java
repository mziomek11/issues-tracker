package org.example.issuestracker.issues.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.issues.command.application.command.RenameIssueCommand;
import org.example.issuestracker.issues.command.application.command.RenameIssueCommand.RenameIssueCommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
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
    public static RenameIssueCommand toCommand(
            UUID issueId,
            IssueOrganizationDetails organizationDetails,
            RenameIssueDto dto
    ) {
        var builder = RenameIssueCommand
                .builder()
                .issueId(issueId)
                .organizationDetails(organizationDetails)
                .issueName(dto.name());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<RenameIssueCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                RenameIssueCommandBuilder.ISSUE_NAME_FIELD_NAME,
                RenameIssueDto.ISSUE_NAME_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
