package org.example.issuestracker.issues.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.issues.command.application.command.ChangeIssueCommentContentCommand;
import org.example.issuestracker.issues.command.application.command.ChangeIssueCommentContentCommand.ChangeIssueCommentContentCommandBuilder;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.ChangeIssueCommentContentDto;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ChangeIssueCommentContentDtoMapper {
    private ChangeIssueCommentContentDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static ChangeIssueCommentContentCommand toCommand(
            UUID issueId,
            UUID commentId,
            ChangeIssueCommentContentDto dto
    ) {
        var builder = ChangeIssueCommentContentCommand
                .builder()
                .issueId(issueId)
                .commentId(commentId)
                .commentContent(dto.commentContent());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<ChangeIssueCommentContentCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                ChangeIssueCommentContentCommandBuilder.COMMENT_CONTENT_FIELD_NAME,
                ChangeIssueCommentContentDto.COMMENT_CONTENT_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
