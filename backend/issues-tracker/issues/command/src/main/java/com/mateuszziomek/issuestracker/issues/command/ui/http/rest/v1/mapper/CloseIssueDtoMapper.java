package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.application.command.CloseIssueCommand;
import com.mateuszziomek.issuestracker.issues.command.application.command.CloseIssueCommand.CloseIssueCommandBuilder;
import com.mateuszziomek.rest.v1.RestValidationErrorsMapper;
import com.mateuszziomek.rest.v1.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CloseIssueDtoMapper {
    private CloseIssueDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static CloseIssueCommand toCommand(
            UUID issueId,
            IssueOrganizationDetails organizationDetails
    ) {
        var builder = CloseIssueCommand
                .builder()
                .issueId(issueId)
                .organizationDetails(organizationDetails);

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<CloseIssueCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
