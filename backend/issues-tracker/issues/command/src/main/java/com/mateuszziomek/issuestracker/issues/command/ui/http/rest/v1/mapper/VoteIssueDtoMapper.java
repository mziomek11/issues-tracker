package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.issues.command.application.command.VoteIssueCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto.VoteIssueDto;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationErrorsMapper;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class VoteIssueDtoMapper {
    private VoteIssueDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static VoteIssueCommand toCommand(
            UUID issueId,
            IssueOrganizationDetails organizationDetails,
            VoteIssueDto dto
    ) {
        var builder = VoteIssueCommand
                .builder()
                .issueId(issueId)
                .organizationDetails(organizationDetails)
                .voteType(dto.voteType());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<VoteIssueCommand.VoteIssueCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                VoteIssueCommand.VoteIssueCommandBuilder.VOTE_TYPE_FIELD_NAME,
                VoteIssueDto.VOTE_TYPE_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
