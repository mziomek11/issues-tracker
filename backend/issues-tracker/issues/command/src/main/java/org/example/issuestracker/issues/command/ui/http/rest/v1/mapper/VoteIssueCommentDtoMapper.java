package org.example.issuestracker.issues.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.issues.command.application.command.VoteIssueCommentCommand;
import org.example.issuestracker.issues.command.application.command.VoteIssueCommentCommand.VoteIssueCommentCommandBuilder;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.VoteIssueCommentDto;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

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
    public static VoteIssueCommentCommand toCommand(UUID issueId, UUID commentId, VoteIssueCommentDto dto) {
        var builder = VoteIssueCommentCommand
                .builder()
                .issueId(issueId)
                .commentId(commentId)
                .voterId(dto.voterId())
                .voteType(dto.voteType());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<VoteIssueCommentCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                VoteIssueCommentCommandBuilder.VOTE_TYPE_FIELD_NAME,
                VoteIssueCommentDto.VOTE_TYPE_FIELD_NAME
        );

        keyMap.put(
                VoteIssueCommentCommandBuilder.VOTER_ID_FIELD_NAME,
                VoteIssueCommentDto.VOTER_ID_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}