package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto.ChangeIssueTypeDto;
import com.mateuszziomek.issuestracker.issues.command.application.command.ChangeIssueTypeCommand;
import com.mateuszziomek.issuestracker.issues.command.application.command.ChangeIssueTypeCommand.ChangeIssueTypeCommandBuilder;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationErrorsMapper;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;

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
            IssueOrganizationDetails organizationDetails,
            ChangeIssueTypeDto dto
    ) {
        var builder = ChangeIssueTypeCommand
                .builder()
                .issueId(issueId)
                .organizationDetails(organizationDetails)
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
