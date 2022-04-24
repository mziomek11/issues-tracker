package com.mateuszziomek.issuestracker.issues.command.domain.vote;

import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Votes {
    private final Set<Vote> voteSet;

    public Votes() {
        this.voteSet = new HashSet<>();
    }

    /**
     * @throws VoteAlreadyExistsException see {@link Votes#ensureCanAdd(Vote)}
     */
    public Votes add(Vote vote) {
        ensureCanAdd(vote);

        var newVotes = voteSet
                .stream()
                .filter(existingVote -> !existingVote.voterId().equals(vote.voterId()))
                .collect(Collectors.toSet());

        newVotes.add(vote);

        return new Votes(newVotes);
    }

    /**
     * @throws VoteAlreadyExistsException if vote with given voter id and type already exists
     */
    public void ensureCanAdd(Vote vote) {
        if (contains(vote)) {
            throw new VoteAlreadyExistsException(vote.voterId(), vote.type());
        }
    }

    private boolean contains(Vote vote) {
        var optionalExistingVote = findVoteByVoterId(vote.voterId());

        if (optionalExistingVote.isEmpty()) {
            return false;
        }

        var existingVote = optionalExistingVote.get();

        return vote.hasTheSameTypeAs(existingVote);
    }

    private Optional<Vote> findVoteByVoterId(VoterId voterId) {
        return voteSet
                .stream()
                .filter(vote -> vote.voterId().equals(voterId))
                .findFirst();
    }
}
