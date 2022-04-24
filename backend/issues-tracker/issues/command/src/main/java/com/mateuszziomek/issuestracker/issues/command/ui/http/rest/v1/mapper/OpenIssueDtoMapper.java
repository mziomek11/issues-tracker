package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.issues.command.application.command.OpenIssueCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto.OpenIssueDto;
import com.mateuszziomek.rest.v1.RestValidationErrorsMapper;
import com.mateuszziomek.rest.v1.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.*;

public class OpenIssueDtoMapper {
    private OpenIssueDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static OpenIssueCommand toCommand(
            UUID issueId,
            IssueOrganizationDetails organizationDetails,
            OpenIssueDto dto
    ) {
        var builder = OpenIssueCommand
                .builder()
                .issueId(issueId)
                .organizationDetails(organizationDetails)
                .issueContent(dto.content())
                .issueName(dto.name())
                .issueType(dto.type());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<OpenIssueCommand.OpenIssueCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                OpenIssueCommand.OpenIssueCommandBuilder.ISSUE_NAME_FIELD_NAME,
                OpenIssueDto.ISSUE_NAME_FIELD_NAME
        );

        keyMap.put(
                OpenIssueCommand.OpenIssueCommandBuilder.ISSUE_CONTENT_FIELD_NAME,
                OpenIssueDto.ISSUE_CONTENT_FIELD_NAME
        );

        keyMap.put(
                OpenIssueCommand.OpenIssueCommandBuilder.ISSUE_TYPE_FIELD_NAME,
                OpenIssueDto.ISSUE_TYPE_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
