package org.example.issuestracker.issues.command.domain.vote;

import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Votes {
    private final Set<Vote> votes;

    public Votes() {
        this.votes = new HashSet<>();
    }

    public Votes(Set<Vote> votes) {
        this.votes = votes;
    }

    public void ensureCanAdd(Vote vote) {
        if (contains(vote)) {
            throw new VoteAlreadyExistsException();
        }
    }

    private boolean contains(Vote vote) {
        var optionalExistingVote = findVoteByVoterId(vote.getVoterId());

        if (optionalExistingVote.isEmpty()) {
            return false;
        }

        var existingVote = optionalExistingVote.get();

        return vote.hasTheSameTypeAs(existingVote);
    }

    public Votes add(Vote newVote) {
        ensureCanAdd(newVote);

        var newVotes = votes
                .stream()
                .filter(vote -> !vote.getVoterId().equals(newVote.getVoterId()))
                .collect(Collectors.toSet());

        newVotes.add(newVote);

        return new Votes(newVotes);
    }

    private Optional<Vote> findVoteByVoterId(VoterId voterId) {
        return votes
                .stream()
                .filter(vote -> vote.getVoterId().equals(voterId))
                .findFirst();
    }
}
