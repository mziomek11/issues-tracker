package org.example.issuestracker.issues.command.domain.vote.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.common.domain.vote.VoteType;

@RequiredArgsConstructor
@Getter
public class VoteAlreadyExistsException extends IllegalStateException {
    private final transient VoterId voterId;
    private final transient VoteType voteType;
}
