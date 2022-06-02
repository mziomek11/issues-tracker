package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.mapper;

import com.mateuszziomek.issuestracker.issues.command.application.command.CommentIssueCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto.CommentIssueDto;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationErrorsMapper;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.validation.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CommentIssueDtoMapper {
    private CommentIssueDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static CommentIssueCommand toCommand(
            UUID issueId,
            UUID commentId,
            IssueOrganizationDetails organizationDetails,
            CommentIssueDto dto
    ) {
        var builder = CommentIssueCommand
                .builder()
                .issueId(issueId)
                .commentId(commentId)
                .organizationDetails(organizationDetails)
                .commentContent(dto.content());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<CommentIssueCommand.CommentIssueCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                CommentIssueCommand.CommentIssueCommandBuilder.COMMENT_CONTENT_FIELD_NAME,
                CommentIssueDto.COMMENT_CONTENT_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
