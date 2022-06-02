package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.issues.command.application.command.RenameIssueCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto.RenameIssueDto;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationErrorsMapper;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;

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
