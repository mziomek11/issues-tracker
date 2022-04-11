package org.example.issuestracker.issues.command.ui.http.rest.v1.mapper;

import org.example.issuestracker.issues.command.application.command.VoteIssueCommand;
import org.example.issuestracker.issues.command.application.command.VoteIssueCommand.VoteIssueCommandBuilder;
import org.example.issuestracker.issues.command.ui.http.rest.v1.dto.VoteIssueDto;
import org.example.rest.v1.RestValidationErrorsMapper;
import org.example.rest.v1.RestValidationException;

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
    public static VoteIssueCommand toCommand(UUID issueId, VoteIssueDto dto) {
        var builder = VoteIssueCommand
                .builder()
                .issueId(issueId)
                .voterId(dto.voterId())
                .voteType(dto.voteType());

        var validationErrors = builder.validate();
        if (!validationErrors.isEmpty()) {
            throw new RestValidationException(toDtoErrors(validationErrors));
        }

        return builder.build();
    }

    public static Map<String, Set<String>> toDtoErrors(
            Set<ConstraintViolation<VoteIssueCommandBuilder>> builderErrors
    ) {
        var keyMap = new HashMap<String, String>();

        keyMap.put(
                VoteIssueCommandBuilder.VOTE_TYPE_FIELD_NAME,
                VoteIssueDto.VOTE_TYPE_FIELD_NAME
        );

        keyMap.put(
                VoteIssueCommandBuilder.VOTER_ID_FIELD_NAME,
                VoteIssueDto.VOTER_ID_FIELD_NAME
        );

        return RestValidationErrorsMapper.toDtoErrors(builderErrors, keyMap);
    }
}
