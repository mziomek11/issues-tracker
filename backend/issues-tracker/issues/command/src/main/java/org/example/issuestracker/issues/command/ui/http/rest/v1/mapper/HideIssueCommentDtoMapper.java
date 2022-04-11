package org.example.issuestracker.issues.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.issues.command.application.command.HideIssueCommentCommand;
import org.example.issuestracker.issues.command.application.command.HideIssueCommentCommand.HideIssueCommentCommandBuilder;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class HideIssueCommentDtoMapper {
    private HideIssueCommentDtoMapper() {}

    /**
     * @throws RestValidationException if dto is not valid
     */
    public static HideIssueCommentCommand toCommand(UUID issueId, UUID commentId) {
        var builder = HideIssueCommentCommand
                .builder()
                .issueId(issueId)
                .commentId(commentId);

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<HideIssueCommentCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
