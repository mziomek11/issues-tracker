package org.example.issuestracker.issues.command.domain.vote.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.shared.domain.valueobject.VoteType;

@RequiredArgsConstructor
@Getter
public class VoteAlreadyExistsException extends IllegalStateException {
    private final transient VoterId voterId;
    private final transient VoteType voteType;
}
