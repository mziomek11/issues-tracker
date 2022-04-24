package com.mateuszziomek.issuestracker.issues.command.domain.vote.exception;

import com.mateuszziomek.issuestracker.issues.command.domain.vote.VoterId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.VoteType;

@RequiredArgsConstructor
@Getter
public class VoteAlreadyExistsException extends IllegalStateException {
    private final transient VoterId voterId;
    private final transient VoteType voteType;
}
