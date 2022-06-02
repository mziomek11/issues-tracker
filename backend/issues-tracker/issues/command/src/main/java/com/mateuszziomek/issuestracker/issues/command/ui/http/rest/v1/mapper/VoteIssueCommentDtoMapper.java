package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.issues.command.application.command.VoteIssueCommentCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.issue.VoteIssueCommentDto;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationErrorsMapper;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class VoteIssueCommentDtoMapper {
    private VoteIssueCommentDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static VoteIssueCommentCommand toCommand(
            UUID issueId,
            UUID commentId,
            IssueOrganizationDetails organizationDetails,
            VoteIssueCommentDto dto
    ) {
        var builder = VoteIssueCommentCommand
                .builder()
                .issueId(issueId)
                .commentId(commentId)
                .organizationDetails(organizationDetails)
                .voteType(dto.voteType());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<VoteIssueCommentCommand.VoteIssueCommentCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                VoteIssueCommentCommand.VoteIssueCommentCommandBuilder.VOTE_TYPE_FIELD_NAME,
                VoteIssueCommentDto.VOTE_TYPE_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
