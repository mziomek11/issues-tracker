package org.example.issuestracker.issues.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.issues.command.application.command.CommentIssueCommand;
import org.example.issuestracker.issues.command.application.command.CommentIssueCommand.CommentIssueCommandBuilder;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.CommentIssueDto;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

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
    public static CommentIssueCommand toCommand(UUID issueId, UUID commentId, CommentIssueDto dto) {
        var builder = CommentIssueCommand
                .builder()
                .issueId(issueId)
                .commentId(commentId)
                .commentContent(dto.content());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<CommentIssueCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                CommentIssueCommandBuilder.COMMENT_CONTENT_FIELD_NAME,
                CommentIssueDto.COMMENT_CONTENT_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
